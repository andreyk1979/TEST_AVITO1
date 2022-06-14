var urlItem = 'http://localhost:8888/api/sales/'

function getAllGrands() {
    let input = document.querySelector('#shopId')
    let id = input.value
    var c = 0;
    let suffixes = event.target.parentNode.parentNode.children[0].innerText;
    console.log(urlItem + id + '/' + suffixes)
    fetch(urlItem + id + '/' + suffixes)
        .then(res => res.json())
        .then(sale => {
            let temp = ''
            let temps = ''
            let name
            sale.sales.forEach(function (p) {
                c = c + p.count
            })
            console.log(c)
            sale.sales.forEach(function (s) {
                name = s.item
                counts = s.count
                temps += `
                  <tr> 
                        <td><b>${name}</b></td>
                        <td>${s.orderDate}</td>
                        <td>${s.count} шт.</td>
                  </tr>`
                document.querySelector('#wholeSales').innerHTML = temps
            });
            temp += `
                  <tr> 
                        <td><b>${name}</b></td>
                        <td>Продано ${c} раз</td>
                        <td>${sale.grandTotalSum} рублей</td>
                        <td class="text-success"><b>${sale.grandTotalProfit} рублей</b></td>
                  </tr>`
            console.log(temp)
            document.querySelector('#itemSales2').innerHTML = temp;
        });

}

getAllGrands();



