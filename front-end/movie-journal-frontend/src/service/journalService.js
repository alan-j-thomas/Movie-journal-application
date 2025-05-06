// src/services/journalService.js
import axios from 'axios';

const API_URL = 'http://localhost:8083/journals';

const JournalService = {
  getAllJournals: () => axios.get(API_URL),
  addJournal: (journalData) => axios.post(API_URL, journalData),
  addToJournal: (data) => axios.post("http://localhost:8083/journals/add", data),
  deleteJournal: (journalId) => axios.delete(`${API_URL}/${journalId}`),
  updateJournal:(editingId, updatedJournal) => axios.put(`${API_URL}/${editingId}`, updatedJournal)
};

export default JournalService;
