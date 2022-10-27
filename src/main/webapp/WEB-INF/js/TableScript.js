function totalPrice() {
    let prices = document.getElementsByClassName('price');
    let sum = 0;

    for (let i = 0; i < prices.length; i++) {
        sum += parseInt(prices.item(i).innerHTML);
    }
    return sum.toString();
}

/**
 * @param {HTMLTableElement} table
 * @param {number} columnId
 * @param {boolean} asc
 */
function sortTableByColumn(table, columnId, asc = true) {
    columnId = columnId + 1;
    const selector = `th:nth-child(` + columnId + `)`;
    const selectorForSort = `td:nth-child(` + columnId + `)`;
    const dirModifier = asc ? 1 : -1;
    const tBody = table.tBodies[0];
    const rows = Array.from(tBody.querySelectorAll("tr"));

    const sortedRows = rows.sort((a, b) => {
        let aColText = a.querySelector(selectorForSort).textContent.trim();
        let bColText = b.querySelector(selectorForSort).textContent.trim();
        if (columnId === 3) {
            aColText = parseInt(aColText.substr(0, aColText.length - 1));
            bColText = parseInt(bColText.substr(0, bColText.length - 1));
        }
        console.log(aColText);
        console.log(bColText);
        return aColText > bColText ? (1 * dirModifier) : (-1 * dirModifier);
    });

    while (tBody.firstChild) {
        tBody.removeChild(tBody.firstChild);
    }

    tBody.append(...sortedRows);

    table.querySelectorAll("th").forEach((th) => {
        th.classList.remove("th-sort-asc");
        th.classList.remove("th-sort-desc")
    });

    if (asc) {
        table.querySelector(selector).classList.add("th-sort-asc");
    } else {
        table.querySelector(selector).classList.add("th-sort-desc");
    }
}

document.querySelectorAll(".table-sortable .columnToSort").forEach(headerCell => {
    headerCell.addEventListener("click", () => {
        const tableElement = headerCell.parentElement.parentElement.parentElement;
        const headerIndex = Array.prototype.indexOf.call(headerCell.parentElement.children, headerCell);
        const currentIsAscending = headerCell.classList.contains("th-sort-asc");

        sortTableByColumn(tableElement, headerIndex, !currentIsAscending);
    });
});

function search() {
    let input, filter, table, tr, td, i, txtValue;
    input = document.getElementById("search");
    filter = input.value.toUpperCase();
    table = document.getElementById("myTable");
    tr = table.getElementsByTagName("tr");
    for (i = 0; i < tr.length; i++) {
        td = tr[i].getElementsByTagName("td")[0];
        if (td) {
            txtValue = td.textContent || td.innerText;
            if (txtValue.toUpperCase().indexOf(filter) > -1) {
                tr[i].style.display = "";
            } else {
                tr[i].style.display = "none";
            }
        }
    }
}

if (document.getElementById("totalPrice")) {
    document.getElementById("totalPrice").innerText = document.getElementById("totalPrice").innerText.replace(': ', ': ' + totalPrice());
}