async function test() {
    let input = document.querySelector('#sb')
    let ins = input.value
    let size = 2;
    let page = -size;
    let countItemAndShop = await fetch("/api/search/count/" + ins);
    let countPage = [];

    countPage = await countItemAndShop.json();

    let searcharea = `<div class="container-fluid">
<div class="row">
<div class="col-md-6" id="itemsearchareaCount">
</div>
<div class="col-md-6" id="shopsearchareaCount">
</div>
<div class="col-md-6" id="itemsearcharea">
</div>
<div class="col-md-6" id="shopsearcharea">
</div>
<div class="row" id="paginationDiv">
<ul id="pagination" class="row-cols-1"></ul>
</div>

</div>

</div>`
    let itemSearchResult = ``
    let shopSearchResult = ``
    let itemSearchResultCount = ``
    let shopSearchResultCount = ``

    itemSearchResultCount = `Товаров найдено: ${countPage[0]}`
    shopSearchResultCount = `Магазинов найдено: ${countPage[1]}`

    async function renderItemShop() {

        shopSearchResult = '';
        itemSearchResult = '';
        let data = await fetch("/api/search/" + ins + "?page=" + page + "&size=" + size)
        try {
            json = await data.json()
        } catch (e) {

            document.querySelector("#main").innerHTML = `НИЧЕГО НЕ НАЙДЕНО`
        }

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
        json = ''
        document.querySelector("#itemsearchareaCount").innerHTML = itemSearchResultCount
        document.querySelector("#shopsearchareaCount").innerHTML = shopSearchResultCount
        document.querySelector("#itemsearcharea").innerHTML = itemSearchResult
        document.querySelector("#shopsearcharea").innerHTML = shopSearchResult

    }

    document.querySelector("#main").innerHTML = searcharea

    let pagination = document.querySelector('#pagination');

    let showPage = (function () {
        let active;

        return function (item) {
            if (active) {
                active.classList.remove('active');
            }

            if (item.innerText === "<<") {
                page = 0;
                item = items[1];
                item.classList.add('active');
                active = item;
            } else if (item.innerText === ">>") {
                page = (items.length - 3) * size;
                item = items[items.length - 2];
                item.classList.add('active');
                active = item;
            } else {
                page = (item.innerHTML - 1) * size;
                item.classList.add('active');
                active = item;
            }

            renderItemShop();
        };
    }());

    let items = [];
    let liBegin = document.createElement("li");
    liBegin.innerHTML = "<<";
    pagination.appendChild(liBegin)
    items.push(liBegin)
    for (let i = 1; i <= countPage[0] / size + 1; i++) {
        let li = document.createElement("li");
        li.innerHTML = i;
        pagination.appendChild(li)
        items.push(li)
    }
    let liEnd = document.createElement("li");
    liEnd.innerHTML = ">>";
    pagination.appendChild(liEnd)
    items.push(liEnd)
    showPage(items[1]);

    for (let item of items) {
        item.addEventListener('click', function () {
            showPage(this);
        });
    }
    json = ''

}

$("#sb").on('input keyup', function () {
    test()
});

