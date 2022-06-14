async function test() {
    let input = document.querySelector('#sb')
    let ins = input.value
    let data = await fetch("/api/search/" + ins)

    let searcharea = ``
    let itemSearchResult = ``
    let shopSearchResult = ``

    try {
        json = await data.json()
    } catch (e) {

        document.querySelector("#main").innerHTML = `НИЧЕГО НЕ НАЙДЕНО`
        document.querySelector("#itemsearcharea").innerHTML = itemSearchResult
        document.querySelector("#shopsearcharea").innerHTML = shopSearchResult
    }

    searcharea = `<div class="container-fluid">
<div class="row">
<div class="col-md-6" id="itemsearcharea">
</div>
<div class="col-md-6" id="shopsearcharea">
</div>
</div>
</div>`

    itemSearchResult = `Товаров найдено: ${json.itemDtoList.length}`
    shopSearchResult = `Магазинов найдено: ${json.shopDtoList.length}`

    json.itemDtoList.forEach(item => {
        itemSearchResult += `<a href="#" class="list-group-item list-group-item-action">
    <div class="d-flex w-100 justify-content-between">
      <h5 class="mb-1">  </h5>
      <small class="text-muted">Id: ${item.id}</small>
    </div>
    <p class="mb-1">${item.name}</p>
    <small class="text-muted">${item.description}</small>
  </a>`
    })

    json.shopDtoList.forEach(shop => {
        shopSearchResult += `
  <a href="http://localhost:8888/shop/${shop.id}" class="list-group-item list-group-item-action">
    <div class="d-flex w-100 justify-content-between">
      <h5 class="mb-1">  </h5>
      <small class="text-muted">Id: ${shop.id}</small>
    </div>
    <p class="mb-1">${shop.name}</p>
    <small class="text-muted">${shop.description}</small>
  </a>`
    })

    document.querySelector("#main").innerHTML = searcharea
    document.querySelector("#itemsearcharea").innerHTML = itemSearchResult
    document.querySelector("#shopsearcharea").innerHTML = shopSearchResult
    json = ''
}

$("#sb").on('input keyup', function () {
    test()
});