package com.example.chat_app

import android.util.Log
import com.example.chat_app.model.AppUser
import com.example.chat_app.model.Message
import com.example.chat_app.model.Room
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


object FirebaseUtils {
    fun addUser(
        user: AppUser,
        onSuccessListener: OnSuccessListener<Void>,
        onFailureListener: OnFailureListener
    ) {
        Firebase.firestore.collection(AppUser.COLLECTION_NAME)
            .document(user.uid!!)
            .set(user)
            .addOnSuccessListener(onSuccessListener)
            .addOnFailureListener(onFailureListener)
    }

    fun getUser(
        uid: String,
        onSuccessListener: OnSuccessListener<DocumentSnapshot>,
        onFailureListener: OnFailureListener
    ) {
        Firebase.firestore.collection(AppUser.COLLECTION_NAME)
            .document(uid)
            .get()
            .addOnSuccessListener(onSuccessListener)
            .addOnFailureListener(onFailureListener)
    }

    fun addRoom(
        room: Room,
        onSuccessListener: OnSuccessListener<Void>,
        onFailureListener: OnFailureListener
    ) {
        val documentReference =
            Firebase.firestore
                .collection(Room.COLLECTION_NAME)
                .document()
        room.id = documentReference.id
        documentReference.set(room)
            .addOnSuccessListener(onSuccessListener)
            .addOnFailureListener(onFailureListener)
    }

    fun getRooms(
        onSuccessListener: OnSuccessListener<QuerySnapshot>,
        onFailureListener: OnFailureListener
    ) {
        Firebase.firestore.collection(Room.COLLECTION_NAME)
            .get()
            .addOnSuccessListener(onSuccessListener)
            .addOnFailureListener(onFailureListener)
    }

    fun addMessage(
        message: Message,
        onSuccessListener: OnSuccessListener<Void>,
        onFailureListener: OnFailureListener
    ) {
        Firebase.firestore.collection(Room.COLLECTION_NAME)
            .document(message.roomId!!)
            .collection(Message.COLLECTION_NAME)
            .document()
            .set(message)
            .addOnSuccessListener(onSuccessListener)
            .addOnFailureListener(onFailureListener)

    }

    fun getMessages(
        roomId: String,
        snapshotListener: EventListener<QuerySnapshot>
    ) {
        Firebase.firestore.collection(Room.COLLECTION_NAME)
            .document(roomId)
            .collection(Message.COLLECTION_NAME)
            .orderBy("dateTime",Query.Direction.DESCENDING)
            .addSnapshotListener(snapshotListener)
    }

}

