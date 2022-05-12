package com.example.achieve_goals.entities

import org.hibernate.Hibernate
import javax.persistence.*

@Entity
@Table(name = "users_avatar")
data class UserAvatar(
    @Id
    @Column(name = "id")
    val id: Long,

    @Column(name = "file_extension")
    var fileExtension: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as UserAvatar

        return id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , fileExtension = $fileExtension )"
    }
}
