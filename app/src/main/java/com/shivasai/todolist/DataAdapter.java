package com.shivasai.todolist;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.shivasai.todolist.Model.Task;

import java.util.ArrayList;
import java.util.List;

 class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.MyViewHolder> {
    private List<Task> taskList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title, description;
        CheckBox checkBox;
        ImageButton deleteButton;



        public MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.title);
            description = view.findViewById(R.id.description);
            checkBox = view.findViewById(R.id.checkBox);
            deleteButton = view.findViewById(R.id.deleteButton);
        }
    }

    public TaskAdapter(List<Task> taskList) {
        this.taskList = taskList;
    }
     private DatabaseHandler db;


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Task task = taskList.get(position);
        holder.title.setText(task.getTitle());
        holder.description.setText(task.getDescription());

        holder.checkBox.setChecked(task.isCompleted());
        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            task.setCompleted(isChecked);
        });
        holder.deleteButton.setOnClickListener(v -> {
            Task deletedTask = taskList.get(position);
            showDeleteConfirmationDialog(v.getContext(), deletedTask);
        });
    }

     private void showDeleteConfirmationDialog(Context context, Task task) {
         AlertDialog.Builder builder = new AlertDialog.Builder(context);
         builder.setTitle("Delete Task");
         builder.setMessage("Are you sure you want to delete this task?");

         builder.setPositiveButton("Delete", (dialog, which) -> {
             db.deleteTask(task);
             taskList.remove(task);
             notifyDataSetChanged();
         });

         builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

         AlertDialog dialog = builder.create();
         dialog.show();
     }



    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public Task getItem(int position) {
        return taskList.get(position);
    }


    public void updateTasks(List<Task> tasks) {
        this.taskList = tasks;
        notifyDataSetChanged();
    }


    public interface OnItemClickListener {
        void onItemClick(int position);
    }


    public interface OnItemLongClickListener {
        void onItemLongClick(int position);
    }

    private OnItemClickListener itemClickListener;
    private OnItemLongClickListener itemLongClickListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.itemClickListener = listener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.itemLongClickListener = listener;
    }
}
