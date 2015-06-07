package com.kazy.copedia.adapter;

import com.kazy.copedia.R;
import com.kazy.copedia.model.Word;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class WordAdapter extends RecyclerView.Adapter<WordAdapter.ViewHolder> {

    private List<Word> words;

    public WordAdapter() {
        this.words = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cell_word, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Context context = holder.rootView.getContext();
        Word word = words.get(position);
        holder.wordView.setText(word.getWord());
        holder.meanView.setText(word.getMean());
    }

    public void add(Word word) {
        words.add(word);
        notifyItemInserted(words.size() - 1);
    }

    public void clear() {
        words.clear();
        notifyDataSetChanged();
    }

    public boolean contains(Word word){
        return words.contains(word);
    }

    public void remove(Word word){
        words.remove(word);
    }

    @Override
    public int getItemCount() {
        return words.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        View rootView;

        @InjectView(R.id.word_view)
        TextView wordView;

        @InjectView(R.id.mean_view)
        TextView meanView;

        public ViewHolder(View itemView) {
            super(itemView);
            rootView = itemView;
            ButterKnife.inject(this, itemView);
        }
    }
}
