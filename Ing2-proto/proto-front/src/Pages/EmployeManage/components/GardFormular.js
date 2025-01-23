import { useState } from "react";

export default function GardFormular() {
    const [startDate, setStartDate] = useState('');
    const [endDate, setEndDate] = useState('');
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);

    const handleSubmit = async (e) => {
        e.preventDefault();
        setLoading(true);
        setError(null);

       
        const formData = new FormData();
        formData.append('startDate', startDate);
        formData.append('endDate', endDate);
        

        try {
            const response = await fetch('http://172.31.253.194:8089/epital-api/frontData', {
                method: 'POST',
                mode:'cors',
                headers: {'Content-Type':'application/json'},
                credentials: 'same-origin',
                body : formData
            });

            if (!response.ok) {
                throw new Error('Erreur lors de l\'envoi des données');
            }

            const result = await response.text();
            console.log('Réponse de l\'API:', result);

            // Réinitialiser le formulaire après un envoi réussi
            setStartDate('');
            setEndDate('');

            alert('Planification envoyée avec succès !');

        } catch (err) {
            console.error('Erreur:', err);
            setError(err.message);
        } finally {
            setLoading(false);
        }
    };

    const handleReset = () => {
        setStartDate('');
        setEndDate('');
        setError(null);
    };

    return (
        <div className="flex flex-row space-x-6 items-center justify-center min-h-screen bg-gray-900 text-white">
            <form
                className="max-w-md mx-auto px-24 py-20 bg-white shadow-md rounded-lg"
                onSubmit={handleSubmit}
            >
                <div>
                    <label className="block text-gray-700 font-bold mb-2">PLANNING</label>
                </div>
                
                {error && (
                    <div className="mb-4 text-red-500">
                        {error}
                    </div>
                )}

                <div className="mb-4">
                    <label 
                        htmlFor="start-date" 
                        className="block text-gray-700 font-bold mb-2"
                    >
                        Date de début
                    </label>
                    <input 
                        type="date" 
                        id="start-date"
                        className="w-full px-3 py-2 border rounded-lg text-black focus:outline-none focus:ring-2 focus:ring-blue-400"
                        onChange={(e) => setStartDate(e.target.value)}
                        value={startDate}
                        required
                    />
                </div>
                
                <div className="mb-4">
                    <label 
                        htmlFor="end-date" 
                        className="block text-gray-700 font-bold mb-2"
                    >
                        Date de fin
                    </label>
                    <input 
                        type="date" 
                        id="end-date"
                        className="w-full px-3 py-2 border rounded-lg text-black focus:outline-none focus:ring-2 focus:ring-blue-400"
                        onChange={(e) => setEndDate(e.target.value)}
                        value={endDate}
                        required
                    />
                </div>
                
                <div className="flex flex-row space-x-4">
                    <div className="flex justify-between">
                        <button 
                            type="submit" 
                            className="bg-blue-400 text-white px-4 py-2 rounded-lg hover:bg-blue-500 transition-colors"
                            disabled={loading}
                        >
                            {loading ? 'Envoi en cours...' : 'Soumettre'}
                        </button>
                    </div>
                    <div>
                        <button 
                            type="button" 
                            onClick={handleReset}
                            className="bg-blue-400 text-white px-4 py-2 rounded-lg hover:bg-blue-500 transition-colors"
                        >
                            Réinitialiser
                        </button>
                    </div>
                </div>
            </form>
        </div>
    );
}