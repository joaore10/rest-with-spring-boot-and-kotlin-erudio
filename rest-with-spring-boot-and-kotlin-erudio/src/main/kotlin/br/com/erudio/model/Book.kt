package br.com.erudio.model

import jakarta.persistence.*

@Entity
@Table(name = "books")
data class Book (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @Column(nullable = false, length = 200)
    var author:String = "",

    @Column(name = "launch_date", nullable = false, length = 80)
    var launchDate: String = "",

    @Column(nullable = false)
    var price: Double = 0.00,

    @Column(nullable = false, length = 200)
    var title: String = ""
)