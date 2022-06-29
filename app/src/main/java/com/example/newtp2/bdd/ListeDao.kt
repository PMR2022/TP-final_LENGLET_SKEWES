package com.example.newtp2.bdd

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.newtp2.models.Item
import com.example.newtp2.models.TodoList
import com.example.newtp2.models.User


@Dao
interface ListeDao {

    // Sauve les TodoLists
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveOrUpdateList(lists : List<TodoList>)

    // Sauve le user
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveOrUpdateUser(pseudo: User)

    // Sauve tous les users
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveOrUpdateUsers(users: List<User>)

    // Sauve les Items
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveOrUpdateItems(items : List<Item>)

    // Récupère les listes d'un user (désigné par son idUser)
    @Query("SELECT * FROM TODOLIST WHERE idUser = :id")
    suspend fun getLists(id: Int) : List<TodoList>

    // Récupère les items appartenant à une liste (désignée par son list_id)
    @Query("SELECT * FROM ITEM WHERE list_id = :id")
    suspend fun getItems(id : Int) : List<Item>

    // Récupère le User grâce à son pseudo
    @Query("SELECT * FROM USER WHERE pseudo = :namePseudo")
    suspend fun getUser(namePseudo : String) : User

    // Récupère le User grâce à son pseudo
    @Query("SELECT * FROM USER")
    suspend fun getUsers() : List<User>

}