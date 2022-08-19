///////////////////////////////////////
//            REST запросы           //
///////////////////////////////////////

// REST запрос в БД на увеличение товара в корзине на один
function addItemToBasket(itemId) {
    const addOneItem = "/api/basket/item/add";
    let item = {"id": itemId}
    let itemToCart = {"countInBasket": 1, item}

    return fetch(addOneItem, {
        method: "PUT",
        headers: {
            Accept: "application/json",
            "Content-Type": "application/json; charset=UTF-8",
        },
        body: JSON.stringify(itemToCart)
    })
}

// REST запрос в БД на уменьшение количества товара в корзине на один
function removeItemFromBasket(itemId) {
    const addOneItem = "/api/basket/item/dec";
    let item = {"id": itemId}
    let itemToCart = {"countInBasket": 1, item}

    return fetch(addOneItem, {
        method: "PUT",
        headers: {
            Accept: "application/json",
            "Content-Type": "application/json; charset=UTF-8",
        },
        body: JSON.stringify(itemToCart)
    })
}

// REST запрос на удаление позиции из корзины
function deleteOneItemFromCart(itemId) {
    const deleteOneItem = "/api/basket/item";
    let item = {"id": itemId}

    fetch(deleteOneItem, {
        method: "DELETE",
        headers: {
            Accept: "application/json",
            "Content-Type": "application/json; charset=UTF-8",
        },
        body: JSON.stringify(item)
    }).then(() => {
        $("#itemInModalAndCart" + itemId).remove();
    })
}

// REST запрос на получение остатков текущего товара по ID товара
function getItemRests(itemId) {
    const getItemRests = "/api/basket/rests";
    let item = {"id": itemId}
    let body = []
    body.push(item)
    return fetch(getItemRests, {
        method: "POST",
        headers: {
            Accept: "application/json",
            "Content-Type": "application/json; charset=UTF-8",
        },
        body: JSON.stringify(body)
    }).then(response => response.json())
}

// REST запрос на получение упрощенного мапа корзины в формате id:count (id товара : количество товара в корзине)
function getCountOfItem() {
    const getBasketMap = "/api/basket/items"
    return fetch(getBasketMap).then(response => response.json())
}

// REST запрос на очистку корзины
function clearTheBasket() {
    const deleteAll = "/api/basket/items";
    return fetch(deleteAll, {
        method: "DELETE",
        headers: {
            Accept: "application/json",
            "Content-Type": "application/json; charset=UTF-8",
        }
    }).then(() => {
        $("#basketBody").children().remove();
    })
}

// REST запрос на удаление позиции из корзины на странице корзины
// После удаления обновляет отображение
function deleteOneItemFromCartOnBasketPage(itemId) {
    const deleteOneItem = "/api/basket/item";
    let item = {"id": itemId}

    fetch(deleteOneItem, {
        method: "DELETE",
        headers: {
            Accept: "application/json",
            "Content-Type": "application/json; charset=UTF-8",
        },
        body: JSON.stringify(item)
    }).then(() => {
        $("#itemrowOnBasketPage" + itemId).remove();
        setCountAndViewOnBasketPage(itemId);
    })
}


//////////////////////////////////////////////
//        Работа с модальными окнами        //
//////////////////////////////////////////////

// Функция добавляет товар в корзину в БД и в модальном окне корзины
function addItemToCart(itemId) {
    // Получаем остатки товара с текущим ID
    getItemRests(itemId).then(count => {
        // Получаем карту корзины
        getCountOfItem().then(map => {
            //Получаем количества товара с текущим ID в корзине
            let currentItemCount = Object.values(map)[Object.keys(map).indexOf(itemId.toString())]
            // Добавляем товар в корзину только в том случае, если он есть на остатках или вообще не указан в базе.
            if (currentItemCount > 0 && currentItemCount < Object.values(count) || currentItemCount == null) {
                // Выполняем запрос в базу
                addItemToBasket(itemId).then(() => {
                    // Обновляем модальное окно с корзиной, если там еще нет карточки с товаром
                    if (!$("#itemInModalAndCart" + itemId).is("#itemInModalAndCart" + itemId)) {
                        addItemToCartModal(itemId);
                    }
                }).then(() => {
                    // Обновляем количество товара и цену
                    setCountAndView(itemId);
                })
            }
        })
    });
}

// Функция уменьшает количество товара в корзине и БД на один.
// Если количество равно нулю, удаляет карточку товара из модального окна
function removeItemFromCart(itemId) {
    // Запрос в БД на удаление
    removeItemFromBasket(itemId).then(() => {
        // Если в БД товар в корзине, а в модбальном окне его нет - добавляем.
        if (!$("#itemInModalAndCart" + itemId).is("#itemInModalAndCart" + itemId)) {
            addItemToCartModal(itemId);
        }
    }).then(() => {
        // Обновляем отображение
        setCountAndView(itemId);
        // Получаем количество товара в корзине в БД
        getCountOfItem().then(map => {
            // Получаем текущее количество товара
            let currentItemCount = Object.values(map)[Object.keys(map).indexOf(itemId.toString())]
            // Если количество товара по БД равно нулю, то удаляем карточк с товаром
            if (currentItemCount == null) {
                $("#itemInModalAndCart" + itemId).remove()
            }
        })
    })
}

// Построение карточки товара в модальном окне
function addItemToCartModal(itemId) {
    // Получаем товар из БД по ID
    const addItemToCartModalById = "/api/item/" + itemId
    fetch(addItemToCartModalById).then(response => response.json()).then(item => {
        // Получаем картинку для обложки
        let image = item.images[0].picture;
        $(
            "<tr style='width: 800px;' id='itemInModalAndCart" + itemId + "'>" +
            "<td style='width: 50px;' class='align-middle'><img onclick='deleteOneItemFromCart(" + itemId + ")' style='max-width: 15px; max-height: 15px' src='/img/deleteIcon.png'></td>" +
            "<td style='width: 150px' class='align-middle'><img style='max-width: 120px; max-height: 120px' src='data:image/png;base64, " + image + "'></td>" +
            "<td style='width: 300px' class='align-middle text-wrap'>" + item.name + "</td>" +
            "<td style='width: 150px' class='align-middle'>" +
            "<button onclick='addItemToCart(" + itemId + ")' id='itemIncreaseButton" + itemId + "' type='button' class='default' style='height: 25px; width: 25px'>+</button>" +
            "<span id='itemCount" + itemId + "'></span>" +
            "<button onclick='removeItemFromCart(" + itemId + ")' id='itemDecreaseButton" + itemId + "' type='button' class='default' style='height: 25px; width: 25px'>-</button>" +
            "</td>" +
            "<td style='width: 150px' class='align-middle'><span id='itemPrice" + itemId + "'></span></td>" +
            "</tr>").appendTo("#basketBody");
    });
}

// Функция обновляет отображение счетчика товара в корзине и цену по формуле (цена*количество)
function setCountAndView(itemId) {
    const addItemToCartModalById = "/api/item/" + itemId
    fetch(addItemToCartModalById).then(response => response.json()).then(item => {
        getCountOfItem().then(map => {
            let price = $("#itemPriceField" + itemId)
            let countItem = $("#itemCountField" + itemId)
            let currentItemCount = Object.values(map)[Object.keys(map).indexOf(itemId.toString())]
            let total = item.price * currentItemCount;

            if (!$("#itemCountField" + itemId).is("#itemCountField" + itemId)) {
                $("<span id='itemCountField" + itemId + "'>&nbsp;&nbsp;&nbsp;" + currentItemCount + "&nbsp;&nbsp;&nbsp;</span>").appendTo("#itemCount" + itemId);
            } else {
                countItem.remove()
                $("<span id='itemCountField" + itemId + "'>&nbsp;&nbsp;&nbsp;" + currentItemCount + "&nbsp;&nbsp;&nbsp;</span>").appendTo("#itemCount" + itemId);
            }

            if (!$("#itemPriceField" + itemId).is("#itemPriceField" + itemId)) {
                $("<span id='itemPriceField" + itemId + "'>" + total + "</span>").appendTo("#itemPrice" + itemId);
            } else {
                price.remove()
                $("<span id='itemPriceField" + itemId + "'>" + total + "</span>").appendTo("#itemPrice" + itemId);
            }
        })
    });
}

// Функция заполняет модальное окно при загрузке страницы
function onLoadBasketFill() {
    getCountOfItem().then(map => {
        for (let id of Object.keys(map)) {
            addItemToCartModal(id);
            setCountAndView(id);
        }
    })
}


/////////////////////////////////////////////////
//         Работа со страницей корзины         //
/////////////////////////////////////////////////

// Функция увеличивает количество товара в корзине на один. Работает на странице корзины
function increaseCountOfItemOnBasketPage(itemId) {
    getItemRests(itemId).then(count => {
        getCountOfItem().then(map => {
            let currentItemCount = Object.values(map)[Object.keys(map).indexOf(itemId.toString())]
            if (currentItemCount > 0 && currentItemCount < Object.values(count) || currentItemCount == null) {
                addItemToBasket(itemId).then(() => {
                    setCountAndViewOnBasketPage(itemId);
                })
            }
        })
    });
}

// Функция уменьшает количество товара в корзине на один. Если количество равно нулю, удаляет.
// Работает на странице корзины
function decreaseCountOfItemOnBasketPage(itemId) {
    removeItemFromBasket(itemId).then(() => {
        setCountAndViewOnBasketPage(itemId);
        getCountOfItem().then(map => {
            let currentItemCount = Object.values(map)[Object.keys(map).indexOf(itemId.toString())]
            if (currentItemCount == null) {
                $("#itemrowOnBasketPage" + itemId).remove()
            }
        })
    })
}

// Функция обновляет счетчик количества товара в корзине, цену, сумму за все позиции в корзине.
function setCountAndViewOnBasketPage(itemId) {
    const addItemToCartModalById = "/api/item/" + itemId
    fetch(addItemToCartModalById).then(response => response.json()).then(item => {
        getCountOfItem().then(map => {
            let price = $("#itemPriceFieldOnBasketPage" + itemId)
            let countItem = $("#itemCountFieldOnBasketPage" + itemId)
            let currentItemCount = Object.values(map)[Object.keys(map).indexOf(itemId.toString())]
            let total = item.price * currentItemCount;
            let orderPriceTotal = 0.00;

            if (!$("#itemCountFieldOnBasketPage" + itemId).is("#itemCountFieldOnBasketPage" + itemId)) {
                $("<span id='itemCountFieldOnBasketPage" + itemId + "'>&nbsp;&nbsp;&nbsp;" + currentItemCount + "&nbsp;&nbsp;&nbsp;</span>").appendTo("#itemCountOnBasketPage" + itemId);
            } else {
                countItem.remove()
                $("<span id='itemCountFieldOnBasketPage" + itemId + "'>&nbsp;&nbsp;&nbsp;" + currentItemCount + "&nbsp;&nbsp;&nbsp;</span>").appendTo("#itemCountOnBasketPage" + itemId);
            }

            if (!$("#itemPriceFieldOnBasketPage" + itemId).is("#itemPriceFieldOnBasketPage" + itemId)) {
                $("<span id='itemPriceFieldOnBasketPage" + itemId + "'>" + total + "</span>").appendTo("#itemPriceOnBasketPage" + itemId);
            } else {
                price.remove()
                $("<span id='itemPriceFieldOnBasketPage" + itemId + "'>" + total + "</span>").appendTo("#itemPriceOnBasketPage" + itemId);
            }

            for (let id of Object.keys(map)) {
                orderPriceTotal += parseInt($("#itemPriceFieldOnBasketPage" + id).text());
            }
            $("#orderPriceTotalAfter").remove()
            $("#orderPriceTotal").after().append("<span id='orderPriceTotalAfter' class=\"text-end fw-bold\"><input type=\"hidden\" name=\"total\" id=\"total\" value=\"" + orderPriceTotal + "\">" + orderPriceTotal + "</span>")
        })
    });
}

// Функция обновляет счетчик количества товара в корзине, цену, сумму
// за все позиции в корзине при загрузке страницы корзины
function onLoadBasketFillOnBasketPage() {
    getCountOfItem().then(map => {
        for (let id of Object.keys(map)) {
            setCountAndViewOnBasketPage(id)
        }
    })
}