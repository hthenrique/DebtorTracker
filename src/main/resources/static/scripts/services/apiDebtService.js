import { HEADERS } from '../config.js';

async function fetchFromApi(endpoint, options) {
    try {
        const response = await fetch(`/api/debts${endpoint}`, options);
        const data = await response.json();
        if (response.status == 200 
            || response.status == 201 
            || response.status == 204) {
            return data;
        }else{
            console.error(`Erro na resposta da API: ${data.response_message}`);
            return null;
        }
    } catch (error) {
        console.error(error);
        return null;
    }
}

export async function loadDebtsByDebtor(id) {
    if (!id) {
        return Promise.reject('UID is required');
    }
    return fetchFromApi('/fetch_debts', {
        method: 'GET',
        headers: { ...HEADERS, id }
    });
}

export async function loadDebtById(id) {
    if (!id) {
        return Promise.reject('UID is required');
    }
    return fetchFromApi('/fetch_debt', {
        method: 'GET',
        headers: { ...HEADERS, id }
    });
}

export async function createDebt(debt) {
    if (!debt) {
        return Promise.reject('Debt is required');
    }
    return fetchFromApi('/create_debt', {
        method: 'POST',
        headers: HEADERS,
        body: JSON.stringify(debt)
    });
}

export async function updateDebt(debt) {
    if (!debt || !debt.id_debt) {
        return Promise.reject('Debt and Debt ID are required');
    }
    return fetchFromApi('/update_debt', {
        method: 'PATCH',
        headers: { ...HEADERS, id: debt.id_debt },
        body: JSON.stringify(debt)
    });
}

export async function deleteDebtById(id) {
    if (!id) {
        return Promise.reject('UID is required');
    }
    return fetchFromApi('/delete_debt', {
        method: 'DELETE',
        headers: { ...HEADERS, id }
    });
}