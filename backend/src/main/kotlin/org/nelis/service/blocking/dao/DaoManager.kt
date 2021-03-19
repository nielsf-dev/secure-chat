package org.nelis.service.blocking.dao

import org.hibernate.SessionFactory
import org.hibernate.boot.MetadataSources
import org.hibernate.boot.model.naming.ImplicitNamingStrategyJpaCompliantImpl
import org.hibernate.boot.registry.BootstrapServiceRegistryBuilder
import org.hibernate.boot.registry.StandardServiceRegistryBuilder
import org.nelis.domain.User

class DaoManager {

    val chatRoomDao:ChatRoomDao

    val chatRoomMessageDao:ChatRoomMessageDao
    val sessionFactory:SessionFactory

    init {
        //https://www.baeldung.com/hibernate-5-bootstrapping-api
        val bootstrapRegistryBuilder = BootstrapServiceRegistryBuilder()
        val bootstrapRegistry = bootstrapRegistryBuilder.build()

        val standardRegistry = StandardServiceRegistryBuilder(bootstrapRegistry)
            .configure() // standaard = hibernate.cfg.xml
            .build()

        val metadata = MetadataSources(standardRegistry)
            .addAnnotatedClass(User::class.java)
            //.addAnnotatedClass(Project::class.java)
            .metadataBuilder
            .applyImplicitNamingStrategy(ImplicitNamingStrategyJpaCompliantImpl.INSTANCE)
            .build()

        sessionFactory = metadata.sessionFactoryBuilder
            .build()

        chatRoomDao = ChatRoomDaoImp(sessionFactory)
        chatRoomMessageDao = ChatRoomMessageDaoImp(sessionFactory)
    }
}