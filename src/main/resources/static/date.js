const urlDate = 'http://localhost:8888/api/sales/date/'

function getAllSalesByDate() {
    let input1 = document.querySelector('#shopId')
    let id = input1.value
    let input = document.querySelector('#date')
    let ins = input.value
    console.log(ins)
    fetch(urlDate + id + '/' + ins)
        .then(res => res.json())
        .then(sal => {
            let temp = '';
            let temp2 = '';
            sal.sales.forEach(function (s) {
                temp2 += `
                  <tr> 
                        <td class="font-weight-bold">${s.item}</td>
                        <td>${s.count} шт.</td>
                        <td>${s.totalSum} рублей</td>
                        <td>${s.basePrice} рублей</td>
                        <td>${s.profit} рублей</td>
                        <td class="font-weight-bold text-success"><b>${s.totalProfit} рублей</b></td>
                  </tr>`
                document.querySelector('#timeSalesItems').innerHTML = temp2;
            })
            temp += `
                  <tr>
                        <td>${sal.grandTotalSum} рублей</td>
                        <td class="text-success"><b>${sal.grandTotalProfit} рублей</b></td>
                  </tr>`
            document.querySelector('#timeSales').innerHTML = temp;
        })
}
