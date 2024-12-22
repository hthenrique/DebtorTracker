function displayDebtorInfo(debtor) {
    const nome = document.getElementById('nome');
    const cpf = document.getElementById('cpf');
    const email = document.getElementById('email');
    const phone = document.getElementById('phone');
    const address = document.getElementById('address');

    if (nome && cpf && email && phone) {
        nome.value = debtor.name;
        cpf.value = debtor.docNumber;
        email.value = debtor.email;
        phone.value = debtor.phone_number;
        address.value = debtor.address;

        maskCPF({ target: cpf });
        maskPhone({ target: phone });

        const title = document.getElementById('info-debtor-title');
        title.textContent = `Informações de ${debtor.name}`;

        const modal = document.getElementById('modal-debtor-info');
        modal.style.display = 'block';

        modal.addEventListener('click', function (event) {
            if (event.target === modal) {
                modal.style.display = 'none';
            }
        });
    } else {
        console.error('Elementos do formulário não encontrados');
    }
}

function displayDebts(debts) {
    const tbody = document.getElementById('list-debts-tbody');

    if (!tbody) {
        console.error('Element with ID "list-debts-tbody" not found');
        return;
    }

    tbody.innerHTML = ''; // Clear existing rows

    debts.forEach(debt => {
        const row = document.createElement('tr');

        row.innerHTML = `
            <td>${debt.id_debt}</td>
            <td>${debt.debt_description}</td>
            <td>
            Valor:${debt.debt}<br>
            Pago:${debt.paid !== undefined && debt.paid !== null ? debt.paid : 0}<br>
            Falta:${debt.debt_missing}<br>
            </td>
            <td>${debt.debt_date}</td>
            <td class="actions">
                ${createButtonEdit(debt.id_debt).outerHTML}
                ${createButtonDelete(debt.id_debt).outerHTML}
            </td>
        `;

        tbody.appendChild(row);
    });
}