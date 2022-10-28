package com.example.exercise4

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity(), PrefectureRecyclerAdapter.OnItemClickListener {
    // 都道府県のリスト
    private val prefectureList = PREFECTURE_LIST.map {
        if (it.contains("東京")) {
            PrefectureRecyclerAdapter.Prefecture(true, it)
        } else {
            PrefectureRecyclerAdapter.Prefecture(prefecture = it)
        }
    }
    private lateinit var adapter: PrefectureRecyclerAdapter // RecyclerViewAdapter
    private var selectedItem: String = "東京都" // 選択済都道府県

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = PrefectureRecyclerAdapter(this, prefectureList)
        findViewById<RecyclerView>(R.id.list).adapter = adapter

        // OKボタンのクリック処理
        findViewById<Button>(R.id.button_ok).setOnClickListener {
            // 都道府県のリストを出力
            Toast.makeText(
                this,
                selectedItem,
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onItemClicked(prefecture: PrefectureRecyclerAdapter.Prefecture) {
        prefectureList.forEach { it.isSelected = false }
        // アイテムの選択状態を選択済にする
        prefecture.isSelected = true
        // 選択されている都道府県を変更
        selectedItem = prefecture.prefecture
        // リストの内容に変更があったことをAdapterに通知
        adapter.notifyDataSetChanged()
    }

    companion object {
        val PREFECTURE_LIST = listOf(
            "北海道","青森県","岩手県","宮城県","秋田県","山形県","福島県",
            "茨城県","栃木県","群馬県","埼玉県","千葉県","東京都","神奈川県",
            "新潟県","富山県","石川県","福井県","山梨県","長野県","岐阜県",
            "静岡県","愛知県","三重県","滋賀県","京都府","大阪府","兵庫県",
            "奈良県","和歌山県","鳥取県","島根県","岡山県","広島県","山口県",
            "徳島県","香川県","愛媛県","高知県","福岡県","佐賀県","長崎県",
            "熊本県","大分県","宮崎県","鹿児島県","沖縄県"
        )
    }
}

class PrefectureRecyclerAdapter(
    private val listener: OnItemClickListener,
    private val prefectures: List<Prefecture>
) : RecyclerView.Adapter<PrefectureRecyclerAdapter.PrefectureViewHolder>() {
    /**
     * 都道府県の情報を保持するデータクラス
     *
     * @property isSelected 選択状態
     * @property prefecture 都道府県名
     */
    data class Prefecture(
        var isSelected: Boolean = false,
        val prefecture: String
    )

    /**
     * Viewの情報を保持するクラス
     *
     * @property itemView
     */
    class PrefectureViewHolder(private val itemView: View) : RecyclerView.ViewHolder(itemView) {
        /**
         * Itemの情報をViewに紐付ける
         */
        fun bind(prefecture: Prefecture, listener: OnItemClickListener) {
            // リストのアイテムのクリック処理
            itemView.rootView.setOnClickListener {
                listener.onItemClicked(prefecture)
            }

            // Viewへの設定
            itemView.findViewById<RadioButton>(R.id.checkbox_prefecture).also {
                it.isChecked = prefecture.isSelected
                it.text = prefecture.prefecture
            }
        }
    }

    /**
     * リストアイテムのクリック処理をコールバックするためのインターフェース
     *
     */
    interface OnItemClickListener {
        fun onItemClicked(prefecture: Prefecture)
    }

    override fun getItemCount(): Int = prefectures.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PrefectureViewHolder {
        // アイテムのViewを生成
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        // ViewHolderを作成
        return PrefectureViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PrefectureViewHolder, position: Int) {
        // データをViewHolderに紐づける
        holder.bind(prefectures[position], listener)
    }
}