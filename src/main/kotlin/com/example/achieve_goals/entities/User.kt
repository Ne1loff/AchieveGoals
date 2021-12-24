package com.example.achieve_goals.entities

import org.hibernate.Hibernate
import org.hibernate.annotations.Cache
import org.hibernate.annotations.CacheConcurrencyStrategy
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import javax.persistence.*

@Entity
@Table(name = "user_table")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
data class User(

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = -1,

    @Column(name="username")
    var usernameSalt: String = "",

    @Column(name="name")
    var name: String? = null,

    @Column(name="surname")
    var surname: String? = null,

    @Column(name="male")
    var male: Boolean = true,

    @Column(name="email")
    var email: String = "",

    @Column(name="locality")
    var locality: Long = 1,

    @Column(name="password")
    var passwordHash: String = "",


    @Column(name="admin")
    var admin: Boolean = false,

    @OneToOne(fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    var userPhoto: UserAvatar? = null

) : UserDetails {

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        val authorityList: MutableList<GrantedAuthority> = ArrayList()
        authorityList.add(SimpleGrantedAuthority("ROLE_USER"))
        if (admin)
            authorityList.add(SimpleGrantedAuthority("ROLE_ADMIN"))
        return authorityList
    }

    override fun getPassword(): String {
        return passwordHash
    }

    override fun getUsername(): String {
        return usernameSalt
    }

    // TODO:
    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as User

        return id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , usernameSalt = $usernameSalt , name = $name , surname = $surname , male = $male , email = $email , locality = $locality , passwordHash = $passwordHash , admin = $admin )"
    }
}
