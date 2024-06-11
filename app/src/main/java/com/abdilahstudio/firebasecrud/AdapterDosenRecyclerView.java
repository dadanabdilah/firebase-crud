package com.abdilahstudio.firebasecrud;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterDosenRecyclerView extends RecyclerView.Adapter<AdapterDosenRecyclerView.ViewHolder> {
    private ArrayList<Dosen> daftarDosen;
    private Context context;

    public AdapterDosenRecyclerView(ArrayList<Dosen> dosens, Context ctx) {
        /*** Inisiasi data dan variabel yang akan digunakan */
        daftarDosen = dosens;
        context = ctx;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        /**
         * Inisiasi View
         * Di tutorial ini kita hanya menggunakan data String untuk tiap item
         * dan juga view nya hanyalah satu TextView
         */
        TextView tvNama;
        TextView tvNik;

        TextView tvJabatan;
        LinearLayout linear;

        ViewHolder(View v) {
            super(v);
            tvNik = v.findViewById(R.id.tv_nikdosen);
            tvNama = v.findViewById(R.id.tv_namadosen);
            tvJabatan = v.findViewById(R.id.tv_jabatadosen);
            linear = v.findViewById(R.id.linear);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        /**
         * Inisiasi ViewHolder
         */
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dosen, parent, false);
        // mengeset ukuran view, margin, padding, dan parameter layout lainnya
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        /**
         * Menampilkan data pada view
         */
        final String nik = daftarDosen.get(position).getNik();
        final String name = daftarDosen.get(position).getNama();
        final String jabatan = daftarDosen.get(position).getJa();
        holder.tvNama.setText(name);
        holder.tvNik.setText(nik);
        holder.tvJabatan.setText(jabatan);

        holder.linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, name, Toast.LENGTH_LONG).show();
            }
        });

        holder.tvNama.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /**
                 * Read detail data
                 */
            }
        });

        holder.tvNama.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                /**
                 * Delete dan update data
                 */
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        /**
         * Mengembalikan jumlah item
         */
        return daftarDosen.size();
    }
}
