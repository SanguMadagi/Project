import React, { useState } from "react";
import { searchProducts } from "../api";

const ProductList = () => {
  const [query, setQuery] = useState("");
  const [products, setProducts] = useState([]);

  const handleSearch = async () => {
    if (!query) return;
    const data = await searchProducts(query);
    setProducts(data);
  };

  return (
    <div>
      <h2>Products</h2>
      <input
        type="text"
        placeholder="Search by name"
        value={query}
        onChange={(e) => setQuery(e.target.value)}
      />
      <button onClick={handleSearch}>Search</button>

      <ul>
        {products.map((p) => (
          <li key={p.id}>
            {p.name} — ₹{p.price}
          </li>
        ))}
      </ul>
    </div>
  );
};

export default ProductList;
