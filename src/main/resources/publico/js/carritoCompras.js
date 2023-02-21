function agregarCarrito(idProducto) {
    const cantidad = parseInt(document.querySelector(`tr[data-producto="${idProducto}"] input[name="cantidad"]`).value);
    // Agregar el producto al carrito con su ID y la cantidad seleccionada
    console.log(`Se agregó ${cantidad} unidades del producto con ID ${idProducto} al carrito.`);
}