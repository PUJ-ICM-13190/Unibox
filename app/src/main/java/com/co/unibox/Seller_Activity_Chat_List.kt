package com.co.unibox

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.text.SimpleDateFormat
import java.util.*

class Seller_Activity_Chat_List : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var chatAdapter: ChatListAdapter
    private lateinit var database: FirebaseDatabase
    private lateinit var currentUserId: String
    private val chatList = mutableListOf<ChatListItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.seller_activity_chat_list)

        currentUserId = FirebaseAuth.getInstance().currentUser?.uid ?: run {
            finish()
            return
        }

        recyclerView = findViewById(R.id.chatListRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        chatAdapter = ChatListAdapter(chatList) { userId ->
            val intent = Intent(this, Shopper_Activity_Chat::class.java)
            intent.putExtra("RECEIVER_ID", userId)
            startActivity(intent)
        }
        recyclerView.adapter = chatAdapter

        database = FirebaseDatabase.getInstance()
        loadChatList()
    }

    private fun loadChatList() {
        val chatListRef = database.getReference("chat_list").child(currentUserId)

        chatListRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                chatList.clear()
                for (chatSnapshot in snapshot.children) {
                    val userId = chatSnapshot.key ?: continue
                    val lastMessage = chatSnapshot.child("lastMessage").getValue(String::class.java) ?: ""
                    val timestamp = chatSnapshot.child("timestamp").getValue(Long::class.java) ?: 0
                    val unread = chatSnapshot.child("unread").getValue(Long::class.java) ?: 0

                    // Obtener información del usuario
                    database.getReference("users").child(userId)
                        .addListenerForSingleValueEvent(object : ValueEventListener {
                            override fun onDataChange(userSnapshot: DataSnapshot) {
                                val username = userSnapshot.child("username").getValue(String::class.java) ?: userId

                                val chatItem = ChatListItem(
                                    userId = userId,
                                    username = username,
                                    lastMessage = lastMessage,
                                    timestamp = timestamp,
                                    unreadCount = unread
                                )
                                chatList.add(chatItem)
                                chatList.sortByDescending { it.timestamp }
                                chatAdapter.notifyDataSetChanged()
                            }

                            override fun onCancelled(error: DatabaseError) {}
                        })
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }
}

data class ChatListItem(
    val userId: String,
    val username: String,
    val lastMessage: String,
    val timestamp: Long,
    val unreadCount: Long
)

class ChatListAdapter(
    private val chatList: List<ChatListItem>,
    private val onItemClick: (String) -> Unit
) : RecyclerView.Adapter<ChatListAdapter.ChatViewHolder>() {

    class ChatViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val username: TextView = view.findViewById(R.id.usernameText)
        val lastMessage: TextView = view.findViewById(R.id.lastMessageText)
        val timestamp: TextView = view.findViewById(R.id.timestampText)
        val unreadCount: TextView = view.findViewById(R.id.unreadCountText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.chat_list_item, parent, false)
        return ChatViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val chat = chatList[position]
        holder.username.text = chat.username
        holder.lastMessage.text = chat.lastMessage
        holder.timestamp.text = formatTimestamp(chat.timestamp)
        if (chat.unreadCount > 0) {
            holder.unreadCount.visibility = View.VISIBLE
            holder.unreadCount.text = chat.unreadCount.toString()
        } else {
            holder.unreadCount.visibility = View.GONE
        }

        holder.itemView.setOnClickListener { onItemClick(chat.userId) }
    }

    override fun getItemCount() = chatList.size

    private fun formatTimestamp(timestamp: Long): String {
        val sdf = SimpleDateFormat("dd/MM/yy HH:mm", Locale.getDefault())
        return sdf.format(Date(timestamp))
    }
}