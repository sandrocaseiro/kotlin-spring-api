package dev.sandrocaseiro.template.repositories

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.NoRepositoryBean
import java.io.Serializable

@NoRepositoryBean
interface BaseRepository<T, ID> : JpaRepository<T, ID>
    where ID: Serializable
