import { HEADERS } from '../config.js';

async function fetchFromApi(endpoint, options) {
    try {
        const response = await fetch(`/api/debtors${endpoint}`, options);
        const data = await response.json();
        return data;
    } catch (error) {
        console.error(error);
        return null;
    }
}

export async function saveDebtorService(debtor) {
    if (!debtor) {
        return Promise.reject('Debtor is required');
    }
    return fetchFromApi('/create_debtor', {
        method: 'POST',
        headers: HEADERS,
        body: JSON.stringify(debtor)
    });
}

export async function updateDebtorService(debtor) {
    if (!debtor || !debtor.id) {
        return Promise.reject('Debtor and Debtor ID are required');
    }
    return fetchFromApi('/update_debtor', {
        method: 'PATCH',
        headers: HEADERS,
        body: JSON.stringify(debtor)
    });
}

export async function fetchDebtors() {
    return fetchFromApi('/fetch_debtors', {
        method: 'GET',
        headers: HEADERS
    });
}

export async function deleteDebtor(id) {
    if (!id) {
        return Promise.reject('UID is required');
    }
    return fetchFromApi(`/delete_debtor`, {
        method: 'DELETE',
        headers: { ...HEADERS, id: id }
    });
}

export async function loadDebtorById(id) {
    if (!id) {
        return Promise.reject('UID is required');
    }
    return fetchFromApi(`/fetch_debtor`, {
        method: 'GET',
        headers: { ...HEADERS, id: id }
    });
}