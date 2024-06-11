package com.abdilahstudio.firebasecrud;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;

public class AdapterDosenRecyclerView extends RecyclerView.Adapter<AdapterDosenRecyclerView.ViewHolder> {
    private ArrayList<Dosen> daftarDosen;
    private Context context;
    private DatabaseReference databaseReference;

    public AdapterDosenRecyclerView(ArrayList<Dosen> dosens, Context ctx) {
        daftarDosen = dosens;
        context = ctx;
        databaseReference = FirebaseDatabase.getInstance().getReference("dosens");
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNama, tvNik, tvJabatan;
        Button btnUpdate, btnHapus;
        LinearLayout linear;

        // Konstruktor kelas ViewHolder
        ViewHolder(View v) {
            super(v);
            tvNik = v.findViewById(R.id.tv_nikdosen);
            tvNama = v.findViewById(R.id.tv_namadosen);
            tvJabatan = v.findViewById(R.id.tv_jabatadosen);
            btnUpdate = v.findViewById(R.id.btnUpdate);
            btnHapus = v.findViewById(R.id.btnHapus);
            linear = v.findViewById(R.id.linear);
        }
    }

    // Metode untuk membuat ViewHolder baru
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dosen, parent, false);
        return new ViewHolder(v);
    }

    // Metode untuk menampilkan data ke ViewHolder
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Dosen dosen = daftarDosen.get(holder.getAdapterPosition());

        holder.tvNama.setText(dosen.getNama());
        holder.tvNik.setText(dosen.getNik());
        holder.tvJabatan.setText(dosen.getJabatan());

        holder.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showUpdateDialog(dosen);
            }
        });

        holder.btnHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteDialog(dosen, holder.getAdapterPosition());
            }
        });
    }

    // Metode untuk mendapatkan jumlah item dalam daftar
    @Override
    public int getItemCount() {
        return daftarDosen.size();
    }

    // Metode untuk menampilkan dialog update data dosen
    private void showUpdateDialog(final Dosen dosen) {
        // Membuat dialog baru
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_update_dosen);
        dialog.setTitle("Update Dosen");

        final EditText etNik = dialog.findViewById(R.id.et_nik);
        final EditText etNama = dialog.findViewById(R.id.et_nama);
        final Spinner etJabatan = dialog.findViewById(R.id.et_jabatan);
        Button btnSave = dialog.findViewById(R.id.btn_save);

        etNik.setText(dosen.getNik());
        etNama.setText(dosen.getNama());

        // Mendapatkan array jabatan
        String[] jabatanArray = context.getResources().getStringArray(R.array.JA);

        // Mencari indeks jabatan berdasarkan data dosen
        int jabatanIndex = -1;
        for (int i = 0; i < jabatanArray.length; i++) {
            if (jabatanArray[i].equals(dosen.getJabatan())) {
                jabatanIndex = i;
                break;
            }
        }

        // Megnatur pilihan spinner ke indeks
        if (jabatanIndex != -1) {
            etJabatan.setSelection(jabatanIndex);
        }

        // Metode saat klik tombol Simpan
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nik = etNik.getText().toString().trim();
                String nama = etNama.getText().toString().trim();
                String jabatan = etJabatan.getSelectedItem().toString().trim();

                if (!nik.isEmpty() && !nama.isEmpty() && !jabatan.isEmpty()) {
                    updateDosen(dosen.getKey(), nik, nama, jabatan);
                    dialog.dismiss();
                } else {
                    Toast.makeText(context, "Silakan isi semua kolom", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Menampilkan dialog
        dialog.show();
        Window window = dialog.getWindow();
        if (window != null) {
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
            layoutParams.copyFrom(window.getAttributes());
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            window.setAttributes(layoutParams);
        }
    }

    // Metode untuk menampilkan dialog hapus data dosen
    private void showDeleteDialog(final Dosen dosen, final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Hapus Dosen");
        builder.setMessage("Apakah Anda yakin ingin menghapus dosen ini?");
        builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteDosen(dosen.getKey(), position);
            }
        });
        builder.setNegativeButton("Tidak", null);
        builder.show();
    }

    // Metode untuk update data dosen di database Firebase
    private void updateDosen(String key, String nik, String nama, String jabatan) {
        DatabaseReference ref = databaseReference.child(key);

        Dosen updatedDosen = new Dosen(nik, nama, jabatan, key);
        ref.setValue(updatedDosen)
                .addOnSuccessListener(aVoid -> Toast.makeText(context, "Dosen berhasil diperbarui", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(context, "Gagal memperbarui dosen", Toast.LENGTH_SHORT).show());
    }

    // Metode untuk hapus data dosen dari database Firebase
    private void deleteDosen(String key, final int position) {
        DatabaseReference ref = databaseReference.child(key);
        ref.removeValue()
                .addOnSuccessListener(aVoid -> {
                    daftarDosen.remove(position);
                    notifyItemRemoved(position);
                    Toast.makeText(context, "Dosen berhasil dihapus", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> Toast.makeText(context, "Gagal menghapus dosen", Toast.LENGTH_SHORT).show());
    }
}
