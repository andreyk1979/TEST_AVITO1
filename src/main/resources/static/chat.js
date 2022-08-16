let stompClient = null
const url1 ='http://localhost:8888/api/user/users/user'
const url2 ='http://localhost:8888/chat/user'
const url3 ='http://localhost:8888/chat/'
const url4 ='http://localhost:8888/chat/messages/'
let userName = ''
let companionName = ''
let chatId = 0
let notification = []

$(document).ready(function connect() {
    let socket = new SockJS('/gs-guide-websocket');
    stompClient = Stomp.over(socket)
    stompClient.connect({}, function (frame) {
        stompClient.subscribe('/topic/greetings', function (greeting) {
            messageProcessing(JSON.parse(greeting.body))
        })
    })
})

/** Получение имени юзера текущей сессии */
fetch(url1)
    .then(res => { res.json().then(
        user => {
            userName = user.username
            document.getElementById("userName").innerHTML = userName
        }
    )
    })

/** Запрос на получение всех чатов юзера */
fetch(url2)
    .then(res => res.json())
    .then(data => showChats(data))

/** Метод добавления всех чатов юзера в chatBar */
const showChats = (chatSet) => {
    let chatBar = document.getElementById('chatBar').innerHTML

    setTimeout(function(){

        chatSet.forEach((chat) => {
            let chatId = chat.id
            let chatCompanionName =''
            let count=0
            let notificationCount = ''
            if (chat.fromUserName === userName) {
                chatCompanionName = chat.toUserName
            } else {
                chatCompanionName = chat.fromUserName}

            chat.messages.forEach((message) => {
                if((!message.viewed) & (message.fromUserName === chatCompanionName)){
                    count++
                    notification.push(chatId)
                }
            })

            if(count > 0) {
                notificationCount = count
            }

            chatBar += `<li class="nav-item">
                        <a class="nav-link list-group-item-action list-group-item-info"
                        href="#chat" data-toggle="tab" id="${chatId}">${chatCompanionName}
                        <span class="badge badge-danger" id="counter${chatId}">${notificationCount}</span></a>
                    </li>`
        })
        document.getElementById("chatBar").innerHTML =chatBar
    }, 200);
}

/** Функция обработки новых сообщений */
function messageProcessing(notification) {
    if(notification.chatId === null & notification.fromUserName === userName) {
        alert('Неверное имя получателя!')
        return
    }
    let nChatId = notification.chatId
    if(notification.fromUserName === userName & notification.chatId != null ) {
        if(document.getElementById(nChatId) == null) {
            addChat(notification)
        }
        setTimeout(function(){
            document.getElementById(nChatId).click();
        }, 100);
    }
    if(notification.toUserName === userName) {
        if(document.getElementById(nChatId) == null) {
            addChat(notification)
        }

        if(chatId == nChatId) {
            chatMessageViewed(nChatId)
            setTimeout(function(){
                document.getElementById(nChatId).click();
            }, 100);
        } else {
            chatNotification(nChatId)
        }
    }
}

/** Метод добавления одного чата в chatBar */
const addChat = (notification) => {
    let chatBar = document.getElementById('chatBar').innerHTML
    let newChatCompanionName = ''
    if (notification.fromUserName === userName) {
        newChatCompanionName = notification.toUserName
    } else {
        newChatCompanionName = notification.fromUserName}

    chatBar += `<li class="nav-item">
                        <a class="nav-link list-group-item-action list-group-item-info"
                        href="#chat" data-toggle="tab" id="${notification.chatId}">${newChatCompanionName}</a>
                    </li>`

    document.getElementById("chatBar").innerHTML =chatBar
}

/** Функция добовления уведомления */
function chatNotification (chatId) {
notification.push(chatId)
    let count = notification.filter(i => i === chatId).length
    if(document.getElementById('counter'+chatId) != null) {
        document.getElementById('counter'+chatId).remove()
    }
    document.getElementById(chatId).innerHTML
        += `<span class="badge badge-danger" id="counter${chatId}">${count}</span>`
}

/** Отправка сообщения из вкладки новый чат*/
$(function () {
    $("#newChatForm").on('submit', function (e) {
        e.preventDefault()
    })
    $( "#sendNewChat" ).click(function() {
        stompClient.send("/app/hello", {}, JSON.stringify({'fromUserName': userName,
            'textMessage': $("#newChatMessage").val(),
            'toUserName':$("#newChatToUserName").val()}));
        newChatMessage.value = ''
        newChatToUserName.value = ''
    })
})

/** Отправка сообщения из вкладки существующего чата*/
$(function () {
    $("#chatForm").on('submit', function (e) {
        e.preventDefault()
    })
    $( "#send" ).click(function() {
        stompClient.send("/app/hello", {}, JSON.stringify({'fromUserName': userName,
            'textMessage': $("#message").val(),
            'toUserName': companionName, 'chatId' : chatId}));
        message.value = ''
    })
})

/** Выбор чата */
chatBar.addEventListener('click', (e) => {
    chatId = e.target.id
    getChatById(chatId)
    if(document.getElementById('counter'+chatId) != null) {
        chatMessageViewed(chatId)
    }

})

/** Запрос на получение чата по id */
function getChatById(id){
    if (id!=='0')
    fetch(url3+id)
        .then(res => res.json())
        .then(data => showMessages(data))
}

/** Отображение сообщений во вкладке чата*/
function showMessages(chat) {
    let messages = ''
    if (chat.fromUserName === userName) {
        companionName = chat.toUserName
    } else {
        companionName = chat.fromUserName}
    chat.messages.forEach((message) =>{

            messages += `<tr><td><em>${message.fromUserName} ${message.creationTime}:</em>  ${message.textMessage}</td></tr>`
    })
    document.getElementById('greetings').innerHTML = messages
}

/** Запрос на изменение статуса сообщений чата*/
function chatMessageViewed(chatId){
    fetch(url4+chatId).then(data => {
        document.getElementById('counter'+chatId).remove()
        notification = notification.filter(i => i !== Number (chatId))
    })
}