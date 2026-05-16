import { useNavigate } from "react-router-dom";

export default function BucketCard({ bucket }) {
  const navigate = useNavigate();

  return (
    <div
      className="bucket-card"
      onClick={() => navigate(`/bucket/${bucket.id}`, { state: { bucketName: bucket.name || bucket.bucketName } })}
    >
      <h3>{bucket.name || bucket.bucketName}</h3>
      <p>{bucket.description || "No description"}</p>
      <span className="param-count">
        {bucket.parameters?.length || 0} parameters
      </span>
    </div>
  );
}