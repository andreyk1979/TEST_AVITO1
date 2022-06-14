const urlDates = 'http://localhost:8888/api/sales/filterByDate/'

function getAllSalesByVariousDates() {
    let input3 = document.querySelector('#shopId')
    let id = input3.value
    let input1 = document.querySelector('#date1')
    let input2 = document.querySelector('#date2')
    let ins1 = input1.value
    let ins2 = input2.value
    console.log(ins1)
    console.log(ins2)
    fetch(urlDates + id + '/' + ins1 + '/' + ins2)
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
                        <td class="text-success"><b>${s.totalProfit} рублей</b></td>
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
