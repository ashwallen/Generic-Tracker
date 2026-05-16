import { useState } from "react";

export default function AuthForm({ type, onSubmit }) {
  const [form, setForm] = useState({
    name: "",
    password: "",
  });

  function handleChange(e) {
    setForm({
      ...form,
      [e.target.name]: e.target.value,
    });
  }

  function handleSubmit(e) {
    e.preventDefault();
    onSubmit(form);
  }

  return (
    <div className="auth-container">
      <form className="auth-form" onSubmit={handleSubmit}>
        <h2>{type === "login" ? "Login" : "Register"}</h2>

        <div className="form-group">
          <label>Name</label>
          <input
            name="name"
            placeholder="Name"
            value={form.name}
            onChange={handleChange}
            required
          />
        </div>

        <div className="form-group">
          <label>Password</label>
          <input
            name="password"
            type="password"
            placeholder="Password"
            value={form.password}
            onChange={handleChange}
            required
          />
        </div>

        <button type="submit" className="btn-submit">
          {type === "login" ? "Login" : "Register"}
        </button>

        <div className="auth-switch">
          {type === "login" ? (
            <p>
              Don't have an account? <a href="/register">Register</a>
            </p>
          ) : (
            <p>
              Already have an account? <a href="/">Login</a>
            </p>
          )}
        </div>
      </form>
    </div>
  );
}