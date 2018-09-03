/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import dao.DAOException;
import domain.Product;
import java.math.BigDecimal;
import gui.helpers.SimpleListModel;
import gui.helpers.ValidationHelper;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import dao.ProductDAO;

/**
 *
 * @author docma436
 */
public class DialogProductEditor extends javax.swing.JDialog {

    private SimpleListModel listModel;
    private final ProductDAO dao;
    private Product product;
    private ValidationHelper validHelp;

    /**
     * Creates new form DialogProductEditor
     */
    public DialogProductEditor(java.awt.Window parent, boolean modal, ProductDAO dao) {
        super(parent);
        super.setModal(modal);
        initComponents();
        
        this.dao = dao;
        
        try {
            listModel = new SimpleListModel(dao.getCategories());
            comboBoxCategory.setModel(listModel);
            comboBoxCategory.setEditable(true);
        
            product = new Product();

            validHelp = new ValidationHelper();
            validHelp.addTypeFormatter(txtPrice, "#0.00", BigDecimal.class);
            validHelp.addTypeFormatter(txtQuantityInStock, "#0", Integer.class);

            validHelp.addPatternFormatter(txtID, Pattern.compile("((^[A-Za-z]{0,2})|(^[A-Za-z]{2}[0-9]{0,4}))"),
                    "ID must consist of a 2 letter prefix and a 4 digit suffix. E.g. AB1234");
        } catch (DAOException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "DAO Exception", JOptionPane.ERROR_MESSAGE);
        }
    }

    // For when we want to edit an existing product.
    public DialogProductEditor(java.awt.Window parent, boolean modal, Product product, ProductDAO dao) {
        this(parent, modal, dao);

        this.product = product;
        this.txtID.setText(product.getProductID());
        this.txtName.setText(product.getName());
        this.txtAreaDescription.setText(product.getDescription());
        this.comboBoxCategory.setSelectedItem(product.getCategory());
        this.txtPrice.setValue(product.getListPrice());
        this.txtQuantityInStock.setValue(product.getQuantityInStock());

        this.txtID.setEditable(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        labelID = new javax.swing.JLabel();
        labelName = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        labelDescription = new javax.swing.JLabel();
        scrollPaneDescription = new javax.swing.JScrollPane();
        txtAreaDescription = new javax.swing.JTextArea();
        labelCategory = new javax.swing.JLabel();
        comboBoxCategory = new javax.swing.JComboBox<>();
        labelPrice = new javax.swing.JLabel();
        labelQuantityInStock = new javax.swing.JLabel();
        buttonSave = new javax.swing.JButton();
        buttonCancel = new javax.swing.JButton();
        txtPrice = new javax.swing.JFormattedTextField();
        txtQuantityInStock = new javax.swing.JFormattedTextField();
        txtID = new javax.swing.JFormattedTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Product Editor");

        labelID.setText("ID:");

        labelName.setText("Name:");

        txtName.setName("txtName"); // NOI18N
        txtName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNameActionPerformed(evt);
            }
        });

        labelDescription.setText("Description:");

        scrollPaneDescription.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        txtAreaDescription.setColumns(20);
        txtAreaDescription.setLineWrap(true);
        txtAreaDescription.setRows(4);
        txtAreaDescription.setName("txtAreaDescription"); // NOI18N
        scrollPaneDescription.setViewportView(txtAreaDescription);

        labelCategory.setText("Category:");

        comboBoxCategory.setName("comboBoxCategory"); // NOI18N

        labelPrice.setText("Price:");

        labelQuantityInStock.setText("Quantity in Stock:");

        buttonSave.setText("Save");
        buttonSave.setName("buttonSave"); // NOI18N
        buttonSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonSaveActionPerformed(evt);
            }
        });

        buttonCancel.setText("Cancel");
        buttonCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCancelActionPerformed(evt);
            }
        });

        txtPrice.setName("txtPrice"); // NOI18N

        txtQuantityInStock.setName("txtQuantityInStock"); // NOI18N

        txtID.setName("txtID"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelQuantityInStock, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(labelPrice, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(labelCategory, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(labelDescription, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(labelName, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(labelID, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtName, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(scrollPaneDescription, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(comboBoxCategory, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtPrice, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtQuantityInStock, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtID, javax.swing.GroupLayout.Alignment.LEADING)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(buttonSave, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(16, 16, 16)
                        .addComponent(buttonCancel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelID)
                    .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelName)
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelDescription)
                    .addComponent(scrollPaneDescription, javax.swing.GroupLayout.DEFAULT_SIZE, 81, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboBoxCategory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelCategory))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelPrice)
                    .addComponent(txtPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelQuantityInStock)
                    .addComponent(txtQuantityInStock, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonCancel)
                    .addComponent(buttonSave))
                .addGap(16, 16, 16))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNameActionPerformed

    private void buttonSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonSaveActionPerformed
        try {
            // Remove initial product from category to stop the modified product showing up in multiple categories.
            dao.removeProductFromCategory(product.getCategory(), product);

            String id = txtID.getText();
            String name = txtName.getText();
            String description = txtAreaDescription.getText();
            String category = (String) comboBoxCategory.getSelectedItem();
            BigDecimal price = (BigDecimal) txtPrice.getValue();
            Integer quantityInStock = (Integer) txtQuantityInStock.getValue();

            // If we're adding a new product, check the product id isn't already in use.
            if (txtID.isEditable()) {
                Product productWithID = dao.getProductByID(id);
                if (productWithID != null) {
                    JOptionPane.showMessageDialog(this, "Product with ID " + id + " already exists!", "Cannot Add Product", JOptionPane.WARNING_MESSAGE);
                    return;
                }
            }

            product.setProductID(id);
            product.setName(name);
            product.setDescription(description);
            product.setCategory(category);
            product.setListPrice(price);
            product.setQuantityInStock(quantityInStock);

            if (validHelp.isObjectValid(product)) {
                dao.saveProduct(product);
                dispose();
            }
        } catch (DAOException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "DAO Exception", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_buttonSaveActionPerformed

    private void buttonCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCancelActionPerformed
        dispose();
    }//GEN-LAST:event_buttonCancelActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonCancel;
    private javax.swing.JButton buttonSave;
    private javax.swing.JComboBox<String> comboBoxCategory;
    private javax.swing.JLabel labelCategory;
    private javax.swing.JLabel labelDescription;
    private javax.swing.JLabel labelID;
    private javax.swing.JLabel labelName;
    private javax.swing.JLabel labelPrice;
    private javax.swing.JLabel labelQuantityInStock;
    private javax.swing.JScrollPane scrollPaneDescription;
    private javax.swing.JTextArea txtAreaDescription;
    private javax.swing.JFormattedTextField txtID;
    private javax.swing.JTextField txtName;
    private javax.swing.JFormattedTextField txtPrice;
    private javax.swing.JFormattedTextField txtQuantityInStock;
    // End of variables declaration//GEN-END:variables
}
