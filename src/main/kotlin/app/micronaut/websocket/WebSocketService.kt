package app.micronaut.websocket

import io.micronaut.http.annotation.PathVariable
import io.micronaut.websocket.WebSocketSession
import io.micronaut.websocket.annotation.*


@ServerWebSocket("/sockets/{topic}") // Définit le chemin du WebSocket
class WebSocketService {

    // Gère la connexion d'un client WebSocket
    @OnOpen
    fun onOpen(
        session: WebSocketSession,
        @PathVariable topic: String // Récupère la valeur du chemin (dans cet exemple, "topic")
    ) {
        println("Connection opened for topic: $topic")
    }

    // Gère la réception de messages du client WebSocket
    @OnMessage
    fun onMessage(
        message: String,
        session: WebSocketSession,
        @PathVariable topic: String
    ) {
        println("Received message from topic $topic: $message")
    }

    // Gère la fermeture de la connexion WebSocket
    @OnClose
    fun onClose(
        session: WebSocketSession,
        @PathVariable topic: String
    ) {
        println("Connection closed for topic: $topic")
    }

    // Gère les erreurs survenues pendant la communication WebSocket
    @OnError
    fun onError(
        throwable: Throwable,
        session: WebSocketSession,
        @PathVariable topic: String
    ) {
        println("Error occurred for topic $topic: ${throwable.message}")
    }
}
