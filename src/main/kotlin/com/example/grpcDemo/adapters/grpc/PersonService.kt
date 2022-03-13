package com.example.grpcDemo.adapters.grpc

import com.example.grpcDemo.adapters.grpc.person.Person.*
import com.example.grpcDemo.adapters.grpc.person.PersonServiceGrpc
import io.grpc.stub.StreamObserver
import org.springframework.stereotype.Service

@Service
class PersonService: PersonServiceGrpc.PersonServiceImplBase() {

    override fun consultaPessoa(request: PersonRequest?, responseObserver: StreamObserver<PersonResponse>?) {
        val person = PersonResponse
            .newBuilder()
            .setName("Nome de teste")
            .setEmail("teste@teste.com")
            .setId(1).build()
        responseObserver?.onNext(person)
        responseObserver?.onCompleted()
    }
}