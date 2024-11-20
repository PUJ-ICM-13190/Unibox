package com.co.unibox

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import com.co.unibox.databinding.ShopperActivityChatBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.database.ValueEventListener
import java.util.Date

class Shopper_Activity_Chat : AppCompatActivity() {
    private val TAG = Shopper_Activity_Chat::class.java.name

    // Variables for Firebase Auth
    private lateinit var mAuth: FirebaseAuth
    private lateinit var currentUser: FirebaseUser

    // Variables for Firebase DB
    private lateinit var database: FirebaseDatabase
    private lateinit var myRef: DatabaseReference
    private lateinit var valueEventListener: ValueEventListener

    // Local Data
    private lateinit var adapter: Activity_MessageAdapter
    private lateinit var sendButton: LottieAnimationView
    private lateinit var scheduleButton: LottieAnimationView
    private lateinit var messageEdit: EditText
    private var messages = ArrayList<Activity_Message>()
    private lateinit var messageList: ListView

    // Chat variables
    private lateinit var receiverUserId: String  // ID del usuario con quien chateas
    private lateinit var chatRoomId: String      // ID único para esta conversación

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ShopperActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtener el ID del usuario receptor desde el Intent
        receiverUserId = intent.getStringExtra("RECEIVER_ID") ?: run {
            Toast.makeText(this, "Error: No se especificó el destinatario", Toast.LENGTH_LONG).show()
            finish()
            return
        }

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance()
        if (mAuth.currentUser == null) {
            Toast.makeText(this, "Usuario no autenticado", Toast.LENGTH_LONG).show()
            finish()
            return
        }
        currentUser = mAuth.currentUser!!

        // Crear un ID único para el chat room (ordenar IDs para asegurar consistencia)
        chatRoomId = createChatRoomId(currentUser.uid, receiverUserId)

        // Initialize Firebase database
        database = FirebaseDatabase.getInstance()
        myRef = database.reference

        // Initialize Views
        sendButton = binding.sendButton
        messageEdit = binding.messageEdit
        messageList = binding.chatMessages
        scheduleButton = binding.scheduleButton

        // Initialize Adapter
        adapter = Activity_MessageAdapter(this@Shopper_Activity_Chat, messages)
        messageList.adapter = adapter

        // Set click listener for send button
        sendButton.setOnClickListener {
            sendMessage()
        }

        scheduleButton.setOnClickListener {
            val intent = Intent(this, Seller_Activity_Schedule_Location::class.java)
            intent.putExtra("RECEIVER_ID", receiverUserId)
            intent.putExtra("CURRENT_USER_ID", currentUser.uid)
            intent.putExtra("CHAT_ROOM_ID", chatRoomId)
            startActivityForResult(intent, 1001) // Código de solicitud único
        }

        // Load existing messages
        loadMessages()
    }

    private fun createChatRoomId(userId1: String, userId2: String): String {
        return if (userId1 < userId2) {
            "${userId1}_${userId2}"
        } else {
            "${userId2}_${userId1}"
        }
    }

    override fun onResume() {
        super.onResume()
        if (mAuth.currentUser == null) {
            Toast.makeText(this, "Sesión expirada", Toast.LENGTH_LONG).show()
            finish()
            return
        }
        loadMessages()
    }

    override fun onPause() {
        super.onPause()
        if (::valueEventListener.isInitialized) {
            myRef.removeEventListener(valueEventListener)
        }
    }

    private fun loadMessages() {
        // Referencia a los mensajes de este chat específico
        myRef = database.getReference("private_chats").child(chatRoomId)

        valueEventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                messages.clear()
                for (snapshot in dataSnapshot.children) {
                    val tmpMsg = snapshot.getValue(Activity_Message::class.java)
                    tmpMsg?.let { messages.add(it) }
                }
                adapter.notifyDataSetChanged()
                messageList.post { messageList.setSelection(messages.size - 1) }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w(TAG, "Error in query", databaseError.toException())
            }
        }
        myRef.addValueEventListener(valueEventListener)
    }

    private fun sendMessage() {
        val messageText = messageEdit.text.toString()
        if (messageText.isNotEmpty()) {
            messageEdit.text.clear()

            // Referencia al chat privado
            val chatRef = database.getReference("private_chats").child(chatRoomId)

            // Generate a new key for the message
            val key = chatRef.push().key ?: return

            // Create the message
            val msgToSend = Activity_Message(
                uuid = key,
                text = messageText,
                userId = currentUser.email,
                timestamp = Date()
            )

            // Save the message
            chatRef.child(key).setValue(msgToSend)
                .addOnSuccessListener {
                    sendButton.playAnimation()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Error al enviar mensaje: ${e.message}", Toast.LENGTH_SHORT).show()
                }

            // Actualizar último mensaje en la lista de chats (opcional)
            updateLastMessage(messageText)
        } else {
            Toast.makeText(this, "El mensaje no puede estar vacío", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateLastMessage(lastMessage: String) {
        val chatListRef = database.getReference("chat_list")

        // Actualizar para el usuario actual
        chatListRef.child(currentUser.uid).child(receiverUserId).apply {
            child("lastMessage").setValue(lastMessage)
            child("timestamp").setValue(ServerValue.TIMESTAMP)
            child("unread").setValue(0)
        }

        // Actualizar para el receptor
        chatListRef.child(receiverUserId).child(currentUser.uid).apply {
            child("lastMessage").setValue(lastMessage)
            child("timestamp").setValue(ServerValue.TIMESTAMP)
            child("unread").setValue(ServerValue.increment(1))
        }
    }
}