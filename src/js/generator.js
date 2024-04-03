document.addEventListener('DOMContentLoaded', function () {
    // Function to validate user input
    function validateInput(count, outputType, filenamePrefix) {
        if (!count || count <= 0 || isNaN(count)) {
            showError('Invalid input: Number of seeds must be a positive integer.');
            return false;
        }
        if (!outputType || !['txt', 'json', 'csv', 'nbt', 'all'].includes(outputType)) {
            showError('Invalid output type. Supported types: txt, json, csv, nbt, all.');
            return false;
        }
        if (!filenamePrefix) {
            showError('Please enter a filename prefix.');
            return false;
        }
        return true;
    }

    // Function to handle AJAX request for seed generation
    function generateSeeds(count, outputType, filenamePrefix) {
        if (!validateInput(count, outputType, filenamePrefix)) {
            return;
        }

        var xhr = new XMLHttpRequest();
        xhr.open('POST', '/generate-seeds', true);
        xhr.setRequestHeader('Content-Type', 'application/json');
        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4) {
                if (xhr.status === 200) {
                    var response = JSON.parse(xhr.responseText);
                    showSuccess(response.message);
                } else {
                    showError('Error: ' + xhr.statusText);
                }
            }
        };
        var data = JSON.stringify({ count: count, outputType: outputType, filenamePrefix: filenamePrefix });
        xhr.send(data);
    }

    // Function to display success message
    function showSuccess(message) {
        var outputDiv = document.getElementById('output');
        outputDiv.innerHTML = '<div class="success">' + message + '</div>';
    }

    // Function to display error message
    function showError(message) {
        var outputDiv = document.getElementById('output');
        outputDiv.innerHTML = '<div class="error">' + message + '</div>';
    }

    // Event listener for form submission
    var form = document.getElementById('seedForm');
    form.addEventListener('submit', function (event) {
        event.preventDefault();
        var count = parseInt(document.getElementById('count').value);
        var outputType = document.getElementById('outputType').value;
        var filenamePrefix = document.getElementById('filenamePrefix').value;
        generateSeeds(count, outputType, filenamePrefix);
    });
});
