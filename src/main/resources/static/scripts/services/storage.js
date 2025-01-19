const Storage = {
    save(key, value) {
        localStorage.setItem(key, value);
    },

    load(key) {
        localStorage.getItem(key);
    },
    
    saveObject(key, value) {
        try {
            const serializedValue = JSON.stringify(value);
            localStorage.setItem(key, serializedValue);
        } catch (error) {
            console.error('Erro ao salvar no localStorage:', error);
        }
    },

    loadObject(key) {
        try {
            const serializedValue = localStorage.getItem(key);
            return serializedValue ? JSON.parse(serializedValue) : null;
        } catch (error) {
            console.error('Erro ao carregar do localStorage:', error);
            return null;
        }
    },

    remove(key) {
        localStorage.removeItem(key);
    },

    clear() {
        localStorage.clear();
    }
};