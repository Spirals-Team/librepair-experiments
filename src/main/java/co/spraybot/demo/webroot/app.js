/* global moment */
/* global Vue */
var data = {
    username: null,
    hasJoinedChat: false,
    usernameError: false,
    newChatMessage: null,
    newChatMessageError: false,
    chatMessages: []
};
document.addEventListener("DOMContentLoaded", function(event) {
    var webSocket;
    var mainChrome = new Vue({
        el: "#main-chrome",
        data,
        computed: {
            showUsernamePrompt() {
                return !this.hasJoinedChat;
            }
        },

        directives: {
            focus: {
                inserted(el) {
                    el.focus();
                }
            }
        },

        methods: {
            joinChat() {
                webSocket = new WebSocket("ws://localhost:8080/chat/" + this.username);
                webSocket.addEventListener("open", function() {

                });

                webSocket.addEventListener("message", function(event) {

                });

                if (!this.username) {
                    this.usernameError = true;
                    return;
                }

                this.hasJoinedChat = true;
            },

            sendChatMessage() {
                if (!this.newChatMessage) {
                    this.newChatMessageError = true;
                    return;
                }
                var chatMessage = {
                    username: this.username,
                    avatar: "icon",
                    message: this.newChatMessage,
                    timestamp: moment()
                };
                this.newChatMessage = null;
                this.chatMessages.unshift(chatMessage);
                var chatMessagesPanel = document.getElementById("received-chat-messages");
                chatMessagesPanel.scrollTop = chatMessagesPanel.scrollHeight;
                webSocket.send(chatMessage.message);
            }
        }
    });


});