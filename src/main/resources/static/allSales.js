const urlList = 'http://localhost:8888/api/sales/'

const on = (element, event, selector, handler) => {
    element.addEventListener(event, e => {
        if (e.target.closest(selector)) {
            handler(e)
        }
    })
}

function getAllSales() {
    let input1 = document.querySelector('#shopId')
    let id = input1.value
    console.log(id)
    fetch(urlList+id)
        .then(res => res.json())
        .then(sales => {
            let temp = '';
            sales.forEach(function (sale) {
                items = sale.item
                temp += `
                  <tr> 
                        <td>${items.name}</td>
                        <td>${sale.orderDate}</td>
                        <td>${sale.count} шт.</td>
                        <td>${sale.basePrice} рублей</td>
                        <td>${sale.price} рублей</td>
                        <td><button type="button" th:attr="${sale.item}" class="btn btn-info" id="${sale.item}" href="http://localhost:8888/api/sales/1/${sale.item}"
                        data-toggle="modal" data-id="${sale.item}" data-target="#exampleModal" onclick="getAllGrands()">Полный отчет</button><td>
                  </tr>`;
            });
            document.querySelector('#allSales').innerHTML = temp;
        });
}
getAllSales();