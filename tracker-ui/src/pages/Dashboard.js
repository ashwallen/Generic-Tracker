import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { getBuckets, createBucket } from "../api/api";
import BucketCard from "../components/BucketCard";

export default function Dashboard() {
  const navigate = useNavigate();
  const [buckets, setBuckets] = useState([]);
  const [showModal, setShowModal] = useState(false);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  const [bucketName, setBucketName] = useState("");
  const [bucketDesc, setBucketDesc] = useState("");
  const [parameters, setParameters] = useState([]);
  const [newParamName, setNewParamName] = useState("");
  const [newParamType, setNewParamType] = useState("TEXT");

  useEffect(() => {
    fetchBuckets();
  }, []);

  const fetchBuckets = async () => {
    try {
      console.log("Fetching buckets...");
      const res = await getBuckets();
      console.log("Buckets response:", res);
      setBuckets(res.data?.data || res.data || []);
    } catch (err) {
      console.error("Buckets error:", err);
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  const addParameter = () => {
    if (!newParamName.trim()) return;
    setParameters([
      ...parameters,
      {
        name: newParamName.trim(),
        dataType: newParamType,
        parameterOrder: parameters.length + 1,
      },
    ]);
    setNewParamName("");
  };

  const removeParameter = (index) => {
    setParameters(parameters.filter((_, i) => i !== index));
  };

  const handleCreateBucket = async (e) => {
    e.preventDefault();
    if (!bucketName.trim() || parameters.length === 0) {
      setError("Name and at least one parameter required");
      return;
    }

    try {
      await createBucket({
        name: bucketName.trim(),
        description: bucketDesc.trim(),
        parameters,
      });
      setShowModal(false);
      setBucketName("");
      setBucketDesc("");
      setParameters([]);
      fetchBuckets();
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
    <div className="dashboard">
      <header className="dashboard-header">
        <h1>My Buckets</h1>
        <button className="btn-logout" onClick={handleLogout}>
          Logout
        </button>
      </header>

      <div className="bucket-actions">
        <button className="btn-primary" onClick={() => setShowModal(true)}>
          + Create Bucket
        </button>
      </div>

      {error && <div className="error-message">{error}</div>}

      {buckets.length === 0 ? (
        <div className="empty-state">
          <p>No buckets yet. Create your first bucket!</p>
        </div>
      ) : (
        <div className="bucket-grid">
          {buckets.map((bucket) => (
            <BucketCard key={bucket.id} bucket={bucket} />
          ))}
        </div>
      )}

      {showModal && (
        <div className="modal-overlay" onClick={() => setShowModal(false)}>
          <div className="modal" onClick={(e) => e.stopPropagation()}>
            <h2>Create New Bucket</h2>
            <form onSubmit={handleCreateBucket}>
              <div className="form-group">
                <label>Bucket Name</label>
                <input
                  type="text"
                  value={bucketName}
                  onChange={(e) => setBucketName(e.target.value)}
                  placeholder="e.g., Gym Tracker"
                  required
                />
              </div>

              <div className="form-group">
                <label>Description (optional)</label>
                <input
                  type="text"
                  value={bucketDesc}
                  onChange={(e) => setBucketDesc(e.target.value)}
                  placeholder="e.g., Track my workouts"
                />
              </div>

              <div className="form-group">
                <label>Parameters</label>
                <div className="param-input-row">
                  <input
                    type="text"
                    value={newParamName}
                    onChange={(e) => setNewParamName(e.target.value)}
                    placeholder="Parameter name"
                  />
                  <select
                    value={newParamType}
                    onChange={(e) => setNewParamType(e.target.value)}
                  >
                    <option value="TEXT">Text</option>
                    <option value="NUMBER">Number</option>
                    <option value="TIME">Time</option>
                  </select>
                  <button type="button" onClick={addParameter}>
                    Add
                  </button>
                </div>

                <div className="param-list">
                  {parameters.map((param, index) => (
                    <div key={index} className="param-item">
                      <span>
                        {param.name} ({param.dataType})
                      </span>
                      <button
                        type="button"
                        onClick={() => removeParameter(index)}
                      >
                        ×
                      </button>
                    </div>
                  ))}
                </div>
              </div>

              <div className="form-actions">
                <button
                  type="button"
                  className="btn-secondary"
                  onClick={() => setShowModal(false)}
                >
                  Cancel
                </button>
                <button type="submit" className="btn-primary">
                  Create Bucket
                </button>
              </div>
            </form>
          </div>
        </div>
      )}
    </div>
  );
}