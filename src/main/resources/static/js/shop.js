// ADD new shop

let addForm = document.querySelector('#addShopForm');
addForm.addEventListener('submit', (e) => {
        let reader = new FileReader();
        let file = document.getElementById('shopLogo').files[0];
        reader.readAsArrayBuffer(file);
        e.preventDefault();
        reader.onload = function () {
            let arrayBuffer = reader.result;
            let byte = new Uint8Array(arrayBuffer);
            let array = Array.from(byte);
            let logoCheck = true;
            console.log(array);
            fetch('/shop/create', {
                method: 'POST',
                headers: {"Content-type": "application/json; charset=UTF-8"},
                body:
                    JSON.stringify({
                        name: document.getElementById('shopName').value,
                        description: document.getElementById('shopDescription').value,
                        email: document.getElementById('shopEmail').value,
                        phone: document.getElementById('shopPhone').value,
                        logo: {
                            picture: array,
                            isMain: logoCheck,
                        },
                        location: {
                            name: document.getElementById('shopCity').value,
                            countryName: document.getElementById('shopCountry').value,
                        },
                        addressDetails: {
                            city: document.getElementById('shopCity').value,
                            cityIndex: document.getElementById('shopIndex').value,
                            street: document.getElementById('shopStreet').value,
                            house: document.getElementById('shopHouse').value,
                            additionalInfo: document.getElementById('shopAddressInfo').value,
                            country: document.getElementById('shopCountry').value
                        }
                    })
            })
                .then(response => {
                    if (response.ok) {
                        document.getElementById('shopName').value = "";
                        document.getElementById('shopDescription').value = "";
                        document.getElementById('shopEmail').value = "";
                        document.getElementById('shopPhone').value = "";
                        document.getElementById('shopLogo').value = "";
                        document.getElementById('shopCity').value = "";
                        document.getElementById('shopIndex').value = "";
                        document.getElementById('shopStreet').value = "";
                        document.getElementById('shopHouse').value = "";
                        document.getElementById('shopCountry').value = "";
                        document.getElementById('shopAddressInfo').value = "";
                        window.alert("Вы успешно прошли регистрацию")
                    } else {
                        window.alert("Во время регистрации ПРОИЗОШЛА ОШИБКА. Попробуйте еще раз либо обратитесь за помощью к модератору")
                    }
                })
        }
    }
)
