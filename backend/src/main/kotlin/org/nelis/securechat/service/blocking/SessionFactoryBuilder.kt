package org.nelis.securechat.service.blocking

import org.hibernate.SessionFactory
import org.hibernate.boot.MetadataSources
import org.hibernate.boot.model.naming.ImplicitNamingStrategyJpaCompliantImpl
import org.hibernate.boot.registry.BootstrapServiceRegistryBuilder
import org.hibernate.boot.registry.StandardServiceRegistryBuilder
import org.nelis.securechat.domain.ChatMessage
import org.nelis.securechat.domain.ChatRoom
import org.nelis.securechat.domain.ChatRoomMessage
import org.nelis.securechat.domain.User

class SessionFactoryBuilder {

    fun build(): SessionFactory{
        val bootstrapRegistryBuilder = BootstrapServiceRegistryBuilder()
        val bootstrapRegistry = bootstrapRegistryBuilder.build()

        val standardRegistry = StandardServiceRegistryBuilder(bootstrapRegistry)
            .configure() // standaard = hibernate.cfg.xml
            .build()

        val metadata = MetadataSources(standardRegistry)
            .addAnnotatedClass(User::class.java)
            .addAnnotatedClass(ChatRoom::class.java)
            .addAnnotatedClass(ChatMessage::class.java)
            .addAnnotatedClass(ChatRoomMessage::class.java)
            .metadataBuilder
            .applyImplicitNamingStrategy(ImplicitNamingStrategyJpaCompliantImpl.INSTANCE)
            .build()

        return metadata.sessionFactoryBuilder.build()
    }
}