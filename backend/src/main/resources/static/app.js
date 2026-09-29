const API_URL = 'http://localhost:8080/api/products';
const FILTERS_URL = 'http://localhost:8080/api/products/filters';
const PLACEHOLDER_IMG = "data:image/svg+xml;charset=UTF-8,%3Csvg%20xmlns%3D%22http%3A%2F%2Fwww.w3.org%2F200%2Fsvg%22%20width%3D%22200%22%20height%3D%22200%22%20viewBox%3D%220%200%20200%20200%22%3E%3Crect%20fill%3D%22%23f1f5f9%22%20width%3D%22200%22%20height%3D%22200%22%2F%3E%3Ctext%20fill%3D%22%2394a3b8%22%20font-family%3D%22sans-serif%22%20font-size%3D%2214%22%20font-weight%3D%22bold%22%20x%3D%2250%25%22%20y%3D%2250%25%22%20dominant-baseline%3D%22middle%22%20text-anchor%3D%22middle%22%3ESin%20Imagen%3C%2Ftext%3E%3C%2Fsvg%3E";

let currentPage = 0;
let totalPages = 1;

// Elementos del DOM
const gridContainer = document.getElementById('product-grid');
const searchInput = document.getElementById('search-input');
const categoryFilter = document.getElementById('category-filter');
const formatFilter = document.getElementById('format-filter');
const prevBtn = document.getElementById('prev-btn');
const nextBtn = document.getElementById('next-btn');
const pageInfo = document.getElementById('page-info');

// Cargar opciones de filtros desde el backend
async function loadFilters() {
    try {
        const response = await fetch(FILTERS_URL);
        if (!response.ok) return;
        const filters = await response.json();

        if (filters.categories) {
            filters.categories.forEach(cat => {
                const opt = document.createElement('option');
                opt.value = cat;
                opt.textContent = cat;
                categoryFilter.appendChild(opt);
            });
        }

        if (filters.formats) {
            filters.formats.forEach(fmt => {
                const opt = document.createElement('option');
                opt.value = fmt;
                opt.textContent = fmt;
                formatFilter.appendChild(opt);
            });
        }
    } catch (err) {
        console.error("Error al cargar filtros:", err);
    }
}

// Cargar productos con paginación, búsqueda y filtros
async function loadProducts(page = 0) {
    gridContainer.innerHTML = '<div class="status-msg">Cargando productos...</div>';

    const params = new URLSearchParams({
        page: page,
        size: 12
    });

    const searchVal = searchInput.value.trim();
    const catVal = categoryFilter.value;
    const fmtVal = formatFilter.value;

    if (searchVal) params.append('search', searchVal);
    if (catVal) params.append('category', catVal);
    if (fmtVal) params.append('format', fmtVal);

    try {
        const response = await fetch(`${API_URL}?${params.toString()}`);
        if (!response.ok) throw new Error('Error en la respuesta del servidor');

        const data = await response.json();
        currentPage = data.number;
        totalPages = data.totalPages;

        renderProducts(data.content);
        updatePaginationControls();
    } catch (err) {
        console.error("Error al obtener productos:", err);
        gridContainer.innerHTML = '<div class="status-msg">Error al conectar con el catálogo de productos.</div>';
    }
}

// Renderizar tarjetas de productos
function renderProducts(products) {
    if (!products || products.length === 0) {
        gridContainer.innerHTML = '<div class="status-msg">No se encontraron productos que coincidan con la búsqueda.</div>';
        return;
    }

    gridContainer.innerHTML = products.map(product => {
        const imgSrc = (product.image && product.image.trim() !== '') ? product.image : PLACEHOLDER_IMG;
        return `
        <div class="card">
            <img src="${imgSrc}" alt="${product.name}" loading="lazy" referrerpolicy="no-referrer" onerror="this.onerror=null;this.src='${PLACEHOLDER_IMG}';" />
            <div class="card-category">${product.category || 'General'}</div>
            <h3 class="card-title">${product.name}</h3>
            <div class="card-price">$${product.price ? product.price.toLocaleString('es-CL') : '0'}</div>
            ${product.format ? `<div class="card-format">Formato: ${product.format}</div>` : ''}
        </div>
        `;
    }).join('');
}

// Actualizar controles de paginación
function updatePaginationControls() {
    pageInfo.textContent = `Página ${currentPage + 1} de ${totalPages || 1}`;
    prevBtn.disabled = currentPage <= 0;
    nextBtn.disabled = currentPage >= totalPages - 1;
}

// Listeners de eventos
let debounceTimer;
searchInput.addEventListener('input', () => {
    clearTimeout(debounceTimer);
    debounceTimer = setTimeout(() => loadProducts(0), 300);
});

categoryFilter.addEventListener('change', () => loadProducts(0));
formatFilter.addEventListener('change', () => loadProducts(0));

prevBtn.addEventListener('click', () => {
    if (currentPage > 0) loadProducts(currentPage - 1);
});

nextBtn.addEventListener('click', () => {
    if (currentPage < totalPages - 1) loadProducts(currentPage + 1);
});

// Inicialización
document.addEventListener('DOMContentLoaded', () => {
    loadFilters();
    loadProducts(0);
});