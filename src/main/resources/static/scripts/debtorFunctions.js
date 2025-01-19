import { saveDebtorService, updateDebtorService, fetchDebtors } from './services/apiDebtorService.js';
import { displayDebtorInfo, createRowDebtor } from './debtor.js';

export function showAddDebtor() {
  displayDebtorInfo(null);
  const debts_container = document.getElementById('debts-container');
  debts_container.style.display = 'none';
}

export function addNewDebt() {
  displayDebtInfo();
}

export function saveDebtor() {
  const errorMessage = document.getElementById('error-message');
  if (errorMessage) {
    errorMessage.style.display = 'none';
  }
  const debtor = getDebtorFormAttributes();
  if (debtor == null) {
    return;
  } else {
    if (debtor.id) {
      updateDebtorService(debtor).then(data => {
        if (data) {
          window.location.reload();
        }
      }).catch(error => console.error(error));
    } else {
      saveDebtorService(debtor).then(data => {
        if (data.response_code === '201') {
          window.location.reload();
        } else {
          console.error('Erro ao processar a resposta da API');
        }
      }).catch(error => console.error(error));
    }
  }
}