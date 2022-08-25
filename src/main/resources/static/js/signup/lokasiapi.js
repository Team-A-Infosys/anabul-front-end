var urlProvinsi = "http://localhost:8081/provinsi.json"
var urlKabupaten = "http://localhost:8081/kota/"
var urlKecamatan = "http://localhost:8081/kecamatan/"
var urlKelurahan = "http://localhost:8081/kelurahan/"

function clearOptions(id) {
    console.log("on clearOptions :" + id)

    //$('#' + id).val(null);
    $("#" + id)
        .empty()
        .trigger("change")
}

console.log("Load Provinsi...")
$.getJSON(urlProvinsi, function (res) {
    res = $.map(res, function (obj) {
        obj.text = obj.nama
        return obj
    })

    data = [
        {
            id: "",
            nama: "- Pilih Provinsi -",
            text: "- Pilih Provinsi -",
        },
    ].concat(res)

    //implemen data ke select provinsi
    $("#provinsi").select2({
        dropdownAutoWidth: true,
        width: "100%",
        data: data,
    })
})

var selectProv = $("#provinsi")
$(selectProv).change(function () {
    var value = $(selectProv).val()
    clearOptions("kabupaten")

    if (value) {
        console.log("on change selectProv")

        var text = $("#provinsi :selected").text()
        console.log("value = " + value + " / " + "text = " + text)

        console.log("Load Kabupaten di " + text + "...")
        $.getJSON(urlKabupaten + value + ".json", function (res) {
            res = $.map(res, function (obj) {
                obj.text = obj.nama
                return obj
            })

            data = [
                {
                    id: "",
                    nama: "- Pilih Kabupaten -",
                    text: "- Pilih Kabupaten -",
                },
            ].concat(res)

            //implemen data ke select provinsi
            $("#kabupaten").select2({
                dropdownAutoWidth: true,
                width: "100%",
                data: data,
            })
        })
    }
})

var selectKab = $("#kabupaten")
$(selectKab).change(function () {
    var value = $(selectKab).val()
    clearOptions("kecamatan")

    if (value) {
        console.log("on change selectKab")

        var text = $("#kabupaten :selected").text()
        console.log("value = " + value + " / " + "text = " + text)

        console.log("Load Kecamatan di " + text + "...")
        $.getJSON(urlKecamatan + value + ".json", function (res) {
            res = $.map(res, function (obj) {
                obj.text = obj.nama
                return obj
            })

            data = [
                {
                    id: "",
                    nama: "- Pilih Kecamatan -",
                    text: "- Pilih Kecamatan -",
                },
            ].concat(res)

            //implemen data ke select provinsi
            $("#kecamatan").select2({
                dropdownAutoWidth: true,
                width: "100%",
                data: data,
            })
        })
    }
})

var selectKec = $("#kecamatan")
$(selectKec).change(function () {
    var value = $(selectKec).val()
    clearOptions("kelurahan")

    if (value) {
        console.log("on change selectKec")

        var text = $("#kecamatan :selected").text()
        console.log("value = " + value + " / " + "text = " + text)

        console.log("Load Kelurahan di " + text + "...")
        $.getJSON(urlKelurahan + value + ".json", function (res) {
            res = $.map(res, function (obj) {
                obj.text = obj.nama
                return obj
            })

            data = [
                {
                    id: "",
                    nama: "- Pilih Kelurahan -",
                    text: "- Pilih Kelurahan -",
                },
            ].concat(res)

            //implemen data ke select provinsi
            $("#kelurahan").select2({
                dropdownAutoWidth: true,
                width: "100%",
                data: data,
            })
        })
    }
})

var selectKel = $("#kelurahan")
$(selectKel).change(function () {
    var value = $(selectKel).val()

    if (value) {
        console.log("on change selectKel")

        var text = $("#kelurahan :selected").text()
        console.log("value = " + value + " / " + "text = " + text)
    }
})