function postJson(json) {
    const url = '/api/images/transform';

    $.ajax({
        url: url,
        type: "POST",
        data: json,
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: success
    });
}

function success(data, textStatus, jqXHR) {
    var rawResponse = data;

    var output = document.getElementById("output");
    output.innerHTML = data;

    var encodedResponse = btoa(rawResponse);

    var img = new Image();
    var container = document.getElementById('transformedImage');

    img.src = 'data:' + jqXHR.getResponseHeader('Content-Type') + ';base64,' + encodedResponse;

    container.appendChild(img);
}

(function () {
    function toJSONString(form) {
        var obj = {};

        // image id
        obj["imageId"] = form.querySelector("#image").value;

        // transformations
        var transformations = [];

        // grayscale
        var grayscale = form.querySelector("#grayscale");
        if (grayscale.checked) {
            transformations.push({"type": "grayscale"});
        }

        // rotate
        var rotate = form.querySelector("#rotate");
        if (rotate.checked) {
            transformations.push({
                "type": "rotate",
                "properties": {
                    "degrees": form.querySelector("#degrees").value
                }
            });
        }

        // resize
        var resize = form.querySelector("#resize");
        if (resize.checked) {
            transformations.push({
                "type": "resize",
                "properties": {
                    "factor": form.querySelector("#factor").value
                }
            });
        }

        // persist
        var persist = form.querySelector("#persist");
        if (persist.checked) {
            obj["persist"] = true
        }

        obj["transformations"] = transformations;

        return JSON.stringify(obj);
    }

    document.addEventListener("DOMContentLoaded", function () {
        var form = document.getElementById("imageTransform");
        var output = document.getElementById("output");
        form.addEventListener("submit", function (e) {
            e.preventDefault();
            var json = toJSONString(this);

            postJson(json);
            //output.innerHTML = json;

        }, false);

    });

})();