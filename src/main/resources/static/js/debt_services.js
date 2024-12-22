function loadDebtsByDebtor(uid) {
    if (uid) {
        const headers = {
            'Content-Type': 'application/json',
            'id': uid
        };

        return fetch(`/api/debts/fetch_debts`, { method: 'GET', headers: headers })
            .then(response => response.json())
            .then(data => {
                if (data.response && data.response.debts) {
                    return data.response.debts;
                } else {
                    console.error('Erro ao processar a resposta da API');
                    return null;
                }
            })
            .catch(error => {
                console.error(error);
                return null;
            });
    } else {
        return Promise.reject('UID is required');
    }
}