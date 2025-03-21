import { useState} from "react";

export default function AbsenceForm() {
        const [AbsenceDate, setAbsenceDate] = useState('');
        const [AbsenceTime, setAbsenceTime] = useState('');
        const [AbsenceReason, setAbsenceReason] = useState('');
        const [loading, setLoading] = useState(false);
        const [error, setError] = useState(null);

        const handleSubmit = async (e) => {
            e.preventDefault();
            setLoading(true);
            setError(null);

            try {
                const response = await fetch('http://172.31.249.204:8089/epital-api/addAbsence', {
                    method: 'POST',
                    mode: 'cors',
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded',
                    },
                    body: `AbsenceDate=${AbsenceDate}&AbsenceTime=${AbsenceTime}&AbsenceReason=${AbsenceReason}`
                });

                if (!response.ok) {
                    throw new Error('Erreur lors de l\'envoi des données');
                }
                const result = await response.text();
                console.log('Réponse de l\'API:', result);

                setAbsenceDate('');
                setAbsenceTime(''); 
                setAbsenceReason('');
                
                alert('Absence ajoutée avec succès !');

            } catch (err) {
                console.error('Erreur:', err);
                setError(err.message);
            } finally {
                setLoading(false);
            }
        }
        const handleReset = () => {
            setAbsenceDate('');
            setAbsenceTime('');
            setAbsenceReason('');
            setError(null);
        }
        

    return(
        <>
       <h1 class="text-white text-lg  font-bold uppercases">
      Déclaration d'absence
      </h1>
            <form 
            class="flex flex-col space-y-4 items-center justify-center border p-4 bg-white shadow-md rounded-lg"
            onSubmit={handleSubmit}
            >
                {error && (
                    <div className="mb-4 text-red-500">
                        {error}
                    </div>
                )}

                <div class="flex flex-row space-x-4">
                    <label class="text-black text-lg italic font-bold">Date d'absence</label>
                    <input 
                    type="date" class="border rounded p-2" 
                    onChange={(e) => setAbsenceDate(e.target.value)}
                    value={AbsenceDate}
                    />
                </div>
                <div class="flex flex-row space-x-4">
                    <label class="text-black text-lg italic font-bold">Heure d'absence</label>
                    <input 
                    type="time" className="border rounded p-2" 
                    onChange={(e) => setAbsenceTime(e.target.value)}
                    value={AbsenceTime}
                    />
                </div>
                <div class="flex flex-row space-x-4">
                    <label class="text-black text-lg italic font-bold">Motif d'absence</label>
                    <textarea 
                    className="border rounded p-2"
                    onChange={(e) => setAbsenceReason(e.target.value)}
                    value={AbsenceReason}
                    />
                </div>
                <div class="flex flex-row space-x-4">
                    <button 
                    type="submit" 
                    class="bg-blue-400 text-white px-4 py-2 rounded"
                    disabled={loading}>
                        {loading ? 'Envoi en cours...' : 'Soumettre'}
                    </button>
                    <button
                     type="reset"
                     onClick={handleReset}
                      class="bg-blue-400 text-white px-4 py-2 rounded">Réinitialiser</button>
                </div>
            </form>
        
        </>
    );
}