import {formatCurrency} from './formatter.js';
import {createButtonEdit, createButtonDelete, editDebt, deleteDebt} from './components/buttons.js';

export function displayDebtInfo(debt){
    const debt_description = document.getElementById('debt_description');
    const debt_date = document.getElementById('debt_date');
    const debt_value = document.getElementById('debt');
    const debt_missing = document.getElementById('debt_missing');
    const debt_paid = document.getElementById('debt_paid');
    const id_debt = document.getElementById('hidden_debt_id');
    const id_debtor = document.getElementById('hidden_debtor_id');
    id_debtor.value = debt.idDebtor;

    if (debt != null){

        id_debt.value = debt.id_debt;

        debt_description.value = debt.debt_description;
        debt_date.value = debt.debt_date;
        debt_value.value = parseFloat(debt.debt).toFixed(2);
        debt_paid.value = parseFloat(debt.debt_paid ?? 0).toFixed(2); // Use nullish coalescing operator to default to 0 if undefined
        debt_missing.value = (parseFloat(debt.debt) - parseFloat(debt.debt_paid ?? 0)).toFixed(2);

        if (debt_missing.value < 0) {
            debt_missing.value = 0;
        }

        formatCurrency(debt_value);
        formatCurrency(debt_missing);
        formatCurrency(debt_paid);
    } else {
        id_debt.value = null;
        debt_description.value = '';
        debt_date.value = '';
        debt_value.value = '';
        debt_missing.value = '';
        debt_paid.value = '';
    }

    const modal = document.getElementById('modal-debt-info');
    modal.style.display = 'block';

    modal.addEventListener('click', function (event) {
        if (event.target === modal) {
            modal.style.display = 'none';
        }
    });
}

export function updateDebtMissing() {
    const debt_value = document.getElementById('debt');
    const debt_paid = document.getElementById('debt_paid');
    const debt_missing = document.getElementById('debt_missing');

    let debt = parseFloat(debt_value.value.replace(/\./g, '').replace(',', '.')) || 0;
    let paid = parseFloat(debt_paid.value.replace(/\./g, '').replace(',', '.')) || 0;
    let missing = debt - paid;

    if (missing < 0) {
        missing = 0;
    }

    debt_missing.value = missing.toFixed(2).replace('.', ',');
    formatCurrency(debt_missing);
}

export function displayDebts(debts) {
    const tbody = document.getElementById('list-debts-tbody');

    if (!tbody) {
        console.error('Element with ID "list-debts-tbody" not found');
        return;
    }

    if (debts.length === 0) {
        console.log('No debts to display');
        const debts_container = document.getElementById('debts-container');
        debts_container.style.display = 'none';
        return;
    }

    debts.forEach(debt => {
        const row = document.createElement('tr');

        row.innerHTML = `
            <td>${debt.id_debt}</td>
            <td>${debt.debt_description}</td>
            <td>
            Valor:${debt.debt}<br>
            Pago:${debt.debt_paid !== undefined && debt.debt_paid !== null ? debt.debt_paid : 0}<br>
            Falta:${debt.debt_missing}<br>
            </td>
            <td>${debt.debt_date}</td>
            <td class="actions">
                ${createButtonEdit(debt.id_debt, 'btn btn-edit edit-debt').outerHTML}
                ${createButtonDelete(debt.id_debt,'btn btn-danger btn-delete delete-debt').outerHTML}
            </td>
        `;
        editDebt(row);
        deleteDebt(row);
        tbody.appendChild(row);
    });
    const debts_container = document.getElementById('debts-container');
    debts_container.style.display = 'block';
}



export function getDebtAttributes(){
    const debt_description = document.getElementById('debt_description');
    const debt_date = document.getElementById('debt_date');
    const debt_value = document.getElementById('debt');
    const debt_missing = document.getElementById('debt_missing');
    const debt_paid = document.getElementById('debt_paid');
    const currentDebt = document.getElementById('hidden_debt_id').value;
    const currentDebtor = document.getElementById('hidden_debtor_id').value;

    if (!debt_description || !debt_date || !debt_value || !debt_missing || !debt_paid) {
        console.error('One or more elements not found');
        return null;
    }

    return {
        id_debt: currentDebt,
        id_debtor: currentDebtor,
        debt_description: debt_description.value,
        debt_date: debt_date.value,
        debt: parseFloat(debt_value.value.replace(/\./g, '').replace(',', '.')).toFixed(2) || 0,
        debt_paid: parseFloat(debt_paid.value.replace(/\./g, '').replace(',', '.')) || 0
    };
}

document.addEventListener('DOMContentLoaded', function() {
    document.getElementById('debt').addEventListener('input', function() {
        formatCurrency(this);
        updateDebtMissing();
    });

    document.getElementById('debt_missing').addEventListener('input', function() {
        formatCurrency(this);
        updateDebtMissing();
    });

    document.getElementById('debt_paid').addEventListener('input', function() {
        formatCurrency(this);
        updateDebtMissing();
    });

    // Expose functions to be called from other classes
    window.displayDebts = displayDebts;
    window.displayDebtInfo = displayDebtInfo;
});
