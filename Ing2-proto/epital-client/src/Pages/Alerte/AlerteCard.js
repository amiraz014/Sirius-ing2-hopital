import React from 'react';
import { FaExclamationTriangle, FaPills } from 'react-icons/fa';

const AlerteCard = ({ alerte, onConfirmerCommande }) => {
  const etatColors = {
    CRITIQUE: 'bg-yellow-100 text-yellow-800',
    ÉPUISÉ: 'bg-red-100 text-red-800',
    NORMAL: 'bg-gray-100 text-gray-800',
  };

  const etatColorClass = etatColors[alerte.etat] || etatColors.NORMAL;

  return (
    <div className="border-l-4 border-yellow-500 p-4 rounded-lg shadow-sm bg-white">
      <div className="flex items-center mb-3">
        <FaPills className="text-blue-500 mr-2" size={20} />
        <h3 className="text-lg font-medium">{alerte.nomMedicament}</h3>
      </div>

      <div className="grid grid-cols-2 gap-1 text-sm text-gray-700 mb-4">
        <span className="text-gray-500">Stock actuel:</span>
        <span>{alerte.stockActuel} unités</span>

        <span className="text-gray-500">Seuil critique:</span>
        <span>{alerte.seuilCritique} unités</span>

        <span className="text-gray-500">À commander:</span>
        <span className="font-bold">{alerte.quantiteACommander} unités</span>
      </div>

      <div className={`inline-flex items-center px-3 py-1 rounded-full mb-4 text-sm font-medium ${etatColorClass}`}>
        <FaExclamationTriangle className="mr-1" />
        {alerte.etat}
      </div>

      <button
        onClick={() => onConfirmerCommande(alerte.medicamentId)}
        disabled={alerte.etat === 'NORMAL'}
        className="w-full bg-blue-600 text-white py-2 px-4 rounded hover:bg-blue-700 disabled:bg-gray-300 transition-colors"
      >
        Confirmer la commande
      </button>
    </div>
  );
};

export default AlerteCard;
