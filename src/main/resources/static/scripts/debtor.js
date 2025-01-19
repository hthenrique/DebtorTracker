import { maskCPF, maskPhone } from './formatter.js';
import { createButtonEdit, createButtonDelete, eventEditButtonClick, eventDeleteButtonClick } from './components/buttons.js';
let currentDebtor;

export function displayDebtorInfo(debtor) {
    const nome = document.getElementById('nome');
    const cpf = document.getElementById('cpf');
    const email = document.getElementById('email');
    const phone = document.getElementById('phone');
    const address = document.getElementById('address');

    if (debtor != null){
        currentDebtor = debtor;
        nome.value = debtor.name;
        cpf.value = debtor.docNumber;
        email.value = debtor.email;
        phone.value = debtor.phone_number;
        address.value = debtor.address;
    } else {
        currentDebtor = null;
        nome.value = '';
        cpf.value = '';
        email.value = '';
        phone.value = '';
        address.value = '';
    }
    
    maskCPF({ target: cpf });
    maskPhone({ target: phone });

    const title = document.getElementById('info-debtor-title');
    if (debtor == null) {
        title.textContent = `Adicionar novo devedor`;
    } else {
        title.textContent = `Informações de ${debtor.name}`;
    }
    const modal = document.getElementById('modal-debtor-info');
    modal.style.display = 'block';

    modal.addEventListener('click', function (event) {
        if (event.target === modal) {
            modal.style.display = 'none';
            document.getElementById('error-message').textContent = null;
            document.getElementById('error-message').style.display = 'none';
        }
    });
}

export function createRowDebtor(debtor) {
    const row = document.createElement('tr');
    row.innerHTML = `
      <td>${debtor.id}</td>
      <td>${debtor.name}</td>
      <td>${maskCPF(debtor.docNumber)}</td>
      <td>${debtor.email}</td>
      <td>${maskPhone(debtor.phone_number)}</td>
      <td class="actions">
        ${createButtonEdit(debtor.id, 'btn btn-edit').outerHTML}
        ${createButtonDelete(debtor.id, 'btn btn-danger btn-delete').outerHTML}
      </td>
    `;
    eventEditButtonClick(row);
    eventDeleteButtonClick(row);
    return row;
  }

export function getDebtorFormAttributes() {
    const nome = document.getElementById('nome').value;
    const cpf = document.getElementById('cpf').value.replace(/\D/g, '');
    const email = document.getElementById('email').value;
    const phone = document.getElementById('phone').value.replace(/\D/g, '');
    const address = document.getElementById('address').value;

    if (fieldValidation(nome, 'Nome') == false 
    || fieldValidation(cpf, 'CPF') == false 
    || fieldValidation(email, 'Email') == false) {
        return null;
    }

    return {
        id: currentDebtor ? currentDebtor.id : null,
        name: nome,
        doc_number: cpf,
        email: email,
        phone_number: phone,
        address: address
    };
}

function fieldValidation(value, field) {
    if (!value && field !== 'Email') {
        document.getElementById('error-message').textContent = `O campo ${field} é obrigatório`;
        document.getElementById('error-message').style.display = 'block';
        return false;
    }
    if(field === 'CPF' && value.length < 11){
        document.getElementById('error-message').textContent = `O campo ${field} está incompleto`;
        document.getElementById('error-message').style.display = 'block';
        return false;
    }
    if(field === 'Email' && value !== '' && !validateEmail(value)){
        document.getElementById('error-message').textContent = `O campo ${field} é inválido`;
        document.getElementById('error-message').style.display = 'block';
        return false;

    }
    return true;
}

function validateEmail(email) {
    const re = /\S+@\S+\.\S+/;
    return re.test(email);
}
