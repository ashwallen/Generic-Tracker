const BASE_URL = "http://localhost:8080/api";

export async function login(data) {
  const response = await fetch(BASE_URL + "/auth/login", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(data),
  });

  const result = await response.json();
  if (!response.ok) throw new Error(result.message || "Login failed");
  return result;
}

export async function register(data) {
  const response = await fetch(BASE_URL + "/auth/register", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(data),
  });

  const result = await response.json();
  if (!response.ok) throw new Error(result.message || "Registration failed");
  return result;
}

function getToken() {
  return localStorage.getItem("token");
}

async function apiRequest(endpoint, method = "GET", body = null) {
  const token = getToken();
  
  const headers = {
    "Content-Type": "application/json",
  };

  if (token) {
    headers["Authorization"] = `Bearer ${token}`;
  }

  const options = {
    method,
    headers,
  };

  if (body) {
    options.body = JSON.stringify(body);
  }

  const response = await fetch(BASE_URL + endpoint, options);
  const result = await response.json();

  if (!response.ok) {
    if (response.status === 401) {
      localStorage.removeItem("token");
      window.location.href = "/";
    }
    throw new Error(result.message || "Request failed");
  }

  return result;
}

export async function getBuckets() {
  return apiRequest("/buckets/get");
}

export async function createBucket(data) {
  return apiRequest("/buckets/create", "POST", data);
}

export async function getBucketEntries(bucketId) {
  return apiRequest(`/entries/bucket/${bucketId}`);
}

export async function createEntry(data) {
  return apiRequest("/entries/create", "POST", {
    bucketId: data.bucketId,
    Date: data.entryDate,
    notes: data.notes,
  });
}

export async function getEntryDetails(entryId) {
  return apiRequest(`/entries/${entryId}/details`);
}

export async function updateEntry(entryId, data) {
  return apiRequest(`/entries/${entryId}`, "PUT", data);
}

export async function deleteEntry(entryId) {
  return apiRequest("/entries/delete", "DELETE", { entryId });
}

export async function saveEntryValues(data) {
  return apiRequest("/entries/insert/rows", "POST", data);
}

export async function deleteRow(rowId) {
  return apiRequest(`/entries/delete/row/${rowId}`, "DELETE");
}