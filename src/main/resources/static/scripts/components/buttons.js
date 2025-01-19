import { loadDebtById, deleteDebtById, loadDebtsByDebtor } from '../services/apiDebtService.js';
import { displayDebtInfo, displayDebts } from '../debt.js';
import { loadDebtorById, deleteDebtor } from '../services/apiDebtorService.js';
import { displayDebtorInfo } from '../debtor.js';

export function createButtonEdit(uid, className) {
  const button = document.createElement('button');
  button.id = 'btn-edit';
  button.className = className;
  button.textContent = 'Editar';
  button.setAttribute('data-uid', uid);
  return button;
}

export function createButtonDelete(uid, className) {
  const button = document.createElement('button');
  button.id = 'btn-delete';
  button.className = className;
  button.textContent = 'Excluir';
  button.setAttribute('data-uid', uid);
  return button;
}

export function editDebt(row) {
  const editButton = row.querySelector('.btn.btn-edit.edit-debt');
  editButton.addEventListener('click', (e) => {
    e.preventDefault();
    const id = editButton.getAttribute('data-uid');
    loadDebtById(id).then(data => {
      if (data) {
        displayDebtInfo(data.response.debt);
      }
    }).catch(error => console.error(error));
  });
}

export function deleteDebt(row) {
  const deleteButton = row.querySelector('.btn.btn-danger.btn-delete.delete-debt');
  deleteButton.addEventListener('click', (e) => {
    e.preventDefault();
    const id = deleteButton.getAttribute('data-uid');
    deleteDebtById(id).then(data => {
      if (data) {
        window.location.reload();
      }
    }).catch(error => console.error(error));
  });
}

export function eventEditButtonClick(row) {
  const editButton = row.querySelector('.btn.btn-edit');
  editButton.addEventListener('click', (e) => {
    e.preventDefault();
    const uid = editButton.getAttribute('data-uid');

    Promise.all([
      loadDebtorById(uid),
      loadDebtsByDebtor(uid)])
      .then(([debtordata, debtsdata]) => {
        if (debtordata) {
          displayDebtorInfo(debtordata.response.debtor);
        }
        if (debtsdata) {
          displayDebts(debtsdata.response.debts);
        } else {
          const tbody = document.getElementById('list-debts-tbody');
          tbody.innerHTML = '';
        }
      }).catch(error => console.error(error));
  });
}

export function eventDeleteButtonClick(row) {
  const deleteButton = row.querySelector('.btn.btn-delete');
  deleteButton.addEventListener('click', (e) => {
    e.preventDefault();
    const uid = deleteButton.getAttribute('data-uid');

    deleteDebtor(uid).then(() => {
      row.remove();
    }).catch(error => console.error(error));
  });
}