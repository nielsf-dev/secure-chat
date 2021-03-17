package org.nelis.service.blocking.dao

import org.hibernate.boot.MetadataSources
import org.hibernate.boot.model.naming.ImplicitNamingStrategyJpaCompliantImpl
import org.hibernate.boot.registry.BootstrapServiceRegistryBuilder
import org.hibernate.boot.registry.StandardServiceRegistryBuilder

class DaoManager {

    val chatRoomDao:ChatRoomDao

    val chatRoomMessageDao:ChatRoomMessageDao

    init {
        //https://www.baeldung.com/hibernate-5-bootstrapping-api
        val bootstrapRegistryBuilder = BootstrapServiceRegistryBuilder()
        val bootstrapRegistry = bootstrapRegistryBuilder.build()

        val standardRegistry = StandardServiceRegistryBuilder(bootstrapRegistry)
            .configure() // standaard = hibernate.cfg.xml
            .build()

        val metadata = MetadataSources(standardRegistry)
            //.addAnnotatedClass(Image::class.java)
            //.addAnnotatedClass(Project::class.java)
            .metadataBuilder
            .applyImplicitNamingStrategy(ImplicitNamingStrategyJpaCompliantImpl.INSTANCE)
            .build()

        val sessionFactory = metadata.sessionFactoryBuilder
            .build()

        chatRoomDao = ChatRoomDaoH(sessionFactory)
        chatRoomMessageDao = ChatRoomMessageDaoH(sessionFactory)
    }
}