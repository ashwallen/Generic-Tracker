import { useParams, useNavigate, useLocation } from "react-router-dom";
import { useEffect, useState } from "react";
import {
  getBucketEntries,
  createEntry,
  getEntryDetails,
  saveEntryValues,
  deleteEntry,
  deleteRow,
} from "../api/api";

const commonTimes = [];
for (let h = 0; h < 24; h++) {
  for (let m = 0; m < 60; m += 30) {
    commonTimes.push(
      `${String(h).padStart(2, "0")}:${String(m).padStart(2, "0")}`
    );
  }
}

export default function BucketDetail() {
  const { id } = useParams();
  const navigate = useNavigate();
  const location = useLocation();
  const bucketNameFromState = location.state?.bucketName || "Bucket";

  const [entries, setEntries] = useState([]);
  const [selectedEntry, setSelectedEntry] = useState(null);
  const [parameters, setParameters] = useState([]);
  const [rows, setRows] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const [entryDate, setEntryDate] = useState(new Date().toISOString().split("T")[0]);
  const [activeTab, setActiveTab] = useState("view");
  const [newRow, setNewRow] = useState({});

  useEffect(() => {
    loadBucketData();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [id]);

  useEffect(() => {
    if (selectedEntry) {
      loadEntryDetails(selectedEntry.entryId);
      setActiveTab("view");
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [selectedEntry]);

  const loadBucketData = async () => {
    try {
      const res = await getBucketEntries(id);
      console.log("Bucket entries response:", res);
      const entriesData = res.data?.data || res.data;
      const entriesArray = Array.isArray(entriesData) ? entriesData : entriesData?.entries || [];
      setEntries(entriesArray);
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  const loadEntryDetails = async (entryId) => {
    try {
      const res = await getEntryDetails(entryId);
      console.log("Entry details response:", res);
      const entryData = res.data?.data || res.data;
      const params = entryData?.parameters || [];
      const existingRows = entryData?.rows || [];

      setParameters(params);

      if (existingRows.length > 0) {
        const transformedRows = existingRows.map((row) => {
          const rowObj = { _rowId: row.rowId };
          row.values.forEach((v) => {
            rowObj[v.parameterId] = v.value;
          });
          return rowObj;
        });
        setRows(transformedRows);
      } else {
        setRows([]);
      }
    } catch (err) {
      setError(err.message);
    }
  };

  const handleCreateEntry = async (e) => {
    e.preventDefault();
    try {
      const res = await createEntry({
        bucketId: id,
        entryDate: entryDate,
        notes: "",
      });
      const newEntry = res.data?.data || res.data;
      setEntries([newEntry, ...entries]);
      setSelectedEntry(newEntry);
      setEntryDate(new Date().toISOString().split("T")[0]);
    } catch (err) {
      setError(err.message);
    }
  };

  const handleDeleteEntry = async (entryId) => {
    if (!window.confirm("Delete this entry?")) return;
    try {
      await deleteEntry(entryId);
      setEntries(entries.filter((e) => e.entryId !== entryId));
      if (selectedEntry?.entryId === entryId) {
        setSelectedEntry(null);
        setRows([]);
        setParameters([]);
      }
    } catch (err) {
      setError(err.message);
    }
  };

  const switchToAddTab = () => {
    const empty = {};
    parameters.forEach((p) => {
      empty[p.parameterId] = "";
    });
    setNewRow(empty);
    setActiveTab("add");
  };

  const updateNewRowValue = (paramId, value) => {
    setNewRow((prev) => ({ ...prev, [paramId]: value }));
  };

  const formatTimeValue = (paramId, raw) => {
    const cleaned = raw.replace(/[^0-9]/g, "");
    if (cleaned.length === 3 || cleaned.length === 4) {
      const hours = cleaned.length === 3 ? "0" + cleaned[0] : cleaned.slice(0, 2);
      const minutes = cleaned.slice(-2);
      const formatted = `${hours}:${minutes}`;
      setNewRow((prev) => ({ ...prev, [paramId]: formatted }));
    }
  };

  const handleSaveNewRow = async () => {
    if (!selectedEntry) return;
    try {
      const payload = {
        entryId: selectedEntry.entryId,
        rows: [
          {
            values: parameters.map((p) => ({
              parameterId: p.parameterId,
              value: newRow[p.parameterId] || "",
            })),
          },
        ],
      };
      await saveEntryValues(payload);
      await loadEntryDetails(selectedEntry.entryId);
      setActiveTab("view");
      alert("Row saved successfully!");
    } catch (err) {
      setError(err.message);
    }
  };

  const handleDeleteRow = async (rowId) => {
    if (!window.confirm("Delete this row?")) return;
    try {
      await deleteRow(rowId);
      await loadEntryDetails(selectedEntry.entryId);
    } catch (err) {
      setError(err.message);
    }
  };

  const handleLogout = () => {
    localStorage.removeItem("token");
    navigate("/");
  };

  if (loading) return <div className="loading">Loading...</div>;

  return (
    <div className="bucket-detail">
      <header className="detail-header">
        <button className="btn-back" onClick={() => navigate("/dashboard")}>
          ← Back
        </button>
        <h1>{bucketNameFromState}</h1>
        <button className="btn-logout" onClick={handleLogout}>
          Logout
        </button>
      </header>

      <div className="detail-content">
        <aside className="entries-sidebar">
          <h3>Entries</h3>
          <form onSubmit={handleCreateEntry} className="new-entry-form">
            <input
              type="date"
              value={entryDate}
              onChange={(e) => setEntryDate(e.target.value)}
              required
            />
            <button type="submit" className="btn-small">
              + New
            </button>
          </form>

          <div className="entries-list">
            {entries.length === 0 ? (
              <p className="empty-text">No entries yet</p>
            ) : (
              entries.map((entry) => (
                <div
                  key={entry.entryId}
                  className={`entry-item ${
                    selectedEntry?.entryId === entry.entryId ? "selected" : ""
                  }`}
                  onClick={() => setSelectedEntry(entry)}
                >
                  <span>{entry.entryDate}</span>
                  <button
                    className="btn-delete"
                    onClick={(e) => {
                      e.stopPropagation();
                      handleDeleteEntry(entry.entryId);
                    }}
                  >
                    ×
                  </button>
                </div>
              ))
            )}
          </div>
        </aside>

        <main className="entry-editor">
          {selectedEntry ? (
            <>
              <div className="editor-header">
                <h3>Entry: {selectedEntry.entryDate}</h3>
              </div>

              <div className="editor-tabs">
                <button
                  className={`tab ${activeTab === "view" ? "active" : ""}`}
                  onClick={() => setActiveTab("view")}
                >
                  View Rows
                </button>
                <button
                  className={`tab ${activeTab === "add" ? "active" : ""}`}
                  onClick={switchToAddTab}
                >
                  Add Row
                </button>
              </div>

              {parameters.length === 0 ? (
                <div className="empty-state">
                  No parameters defined. Add parameters in bucket settings.
                </div>
              ) : activeTab === "view" ? (
                <div className="rows-table">
                  <div className="table-header">
                    {parameters.map((p) => (
                      <div key={p.parameterId} className="th">
                        {p.name}
                      </div>
                    ))}
                    <div className="th actions-col"></div>
                  </div>

                  {rows.length === 0 ? (
                    <div className="empty-row">No rows yet</div>
                  ) : (
                    rows.map((row, rowIndex) => (
                      <div key={rowIndex} className="table-row">
                        {parameters.map((p) => (
                          <div key={p.parameterId} className="td">
                            {row[p.parameterId] || ""}
                          </div>
                        ))}
                        <div className="td actions-col">
                          <button
                            className="btn-remove"
                            onClick={() => handleDeleteRow(row._rowId)}
                          >
                            ×
                          </button>
                        </div>
                      </div>
                    ))
                  )}
                </div>
              ) : (
                <div className="add-row-form">
                  <h4>New Row</h4>
                  <div className="add-row-fields">
                    {parameters.map((p) => (
                      <div key={p.parameterId} className="field-group">
                        <label>{p.name}</label>
                        {p.dataType === "TIME" ? (
                          <>
                            <input
                              type="text"
                              placeholder="HH:MM (e.g. 14:30)"
                              value={newRow[p.parameterId] || ""}
                              onChange={(e) =>
                                updateNewRowValue(p.parameterId, e.target.value)
                              }
                              onBlur={(e) =>
                                formatTimeValue(p.parameterId, e.target.value)
                              }
                              list={`time-suggestions-${p.parameterId}`}
                            />
                            <datalist id={`time-suggestions-${p.parameterId}`}>
                              {commonTimes.map((t) => (
                                <option key={t} value={t} />
                              ))}
                            </datalist>
                          </>
                        ) : p.dataType === "NUMBER" ? (
                          <input
                            type="number"
                            value={newRow[p.parameterId] || ""}
                            onChange={(e) =>
                              updateNewRowValue(p.parameterId, e.target.value)
                            }
                          />
                        ) : (
                          <input
                            type="text"
                            value={newRow[p.parameterId] || ""}
                            onChange={(e) =>
                              updateNewRowValue(p.parameterId, e.target.value)
                            }
                          />
                        )}
                      </div>
                    ))}
                  </div>
                  <div className="editor-actions">
                    <button className="btn-primary" onClick={handleSaveNewRow}>
                      Save Row
                    </button>
                  </div>
                </div>
              )}
            </>
          ) : (
            <div className="empty-state">
              <p>Select an entry or create a new one</p>
            </div>
          )}
        </main>
      </div>

      {error && <div className="error-message">{error}</div>}
    </div>
  );
}
