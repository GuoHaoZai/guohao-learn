# 根据json生成excel

## 思路梳理

## 示例

期望表头
```csv
商品id, 商品名, 商品类型, 试听视频信息(id, 视频名)-多行, 年级-多个
```

```json
{
    "export_file_name" : "商品信息", // 导出excel文件名
    "db_table_name" : "product", // 表名
    "db_filters" : "id IN (1,2,3)", // where条件
    "export_columns_info" : [ // 导出列信息
        {
            "export_column_name" : "商品id",
            "export_column_count" : "single", // excel列数: single, multi
            "export_row_count" : "single", // excel行数: single, multi
            "db_column_name" : "id",
        },
        {
            "export_column_name" : "商品姓名",
            "export_column_count" : "single", 
            "export_row_count" : "single",
            "db_column_name" : "name",
        },
        {
            "export_column_name" : "商品类型",
            "export_column_count" : "single", 
            "export_row_count" : "single",
            "export_mapping_value" : { // excel值映射，用于枚举
                "1" : "学习卡",
                "2" : "小班卡",
            },
            "db_column_name" : "type",
        },
        {
            "export_column_name" : "试听视频信息",
            "export_column_count" : "multi", 
            "export_row_count" : "multi",
            "db_column_name" : "trail_video_ids",
            "db_relate_table_info" : { // 列关联表信息
                "db_table_name" : "hikari_video", // 关联表名
                "db_column_name" : "id", // 关联列名
                "db_column_value_processor" : {
                  "strategy": "SPLITTER", // 列取值策略: json_path(json数组解析), spliter(分隔符),db_column_value直接取值
                  "params": ",",
                },
                "db_table_relate" : "multi", // 关联关系: single, multi 
                "export_columns_info" : [
                    {
                        "export_column_name" : "视频id",
                        "export_column_count" : "single",
                        "export_row_count" : "single",
                        "db_column_name" : "id",
                        
                    },
                    {
                        "export_column_name" : "视频名",
                        "export_column_count" : "single",
                        "export_row_count" : "single",
                        "db_column_name" : "name",
                    }
                ]
            }
        },
        {
            "export_column_name" : "年级",
            "export_column_count" : "single", 
            "export_row_count" : "single",
            "excel_multi_merge" : { // 当export_column_count/export_row_count为single，但是有多行/列数据时
                "strategy" : "by_row", // by_column根据列处理, by_row根据行处理
                "column_spliter" : ",",
                "row_spliter" : "\n"
            },
            "db_column_name" : "id",
            "db_relate_table_info" : {
                "db_table_name" : "product_multi_config", 
                "db_column_name" : "product_id",
                "db_column_value_strategy" : "db_column_value",
                "db_filters" : "type=2", // 年级
                "db_table_relate" : "multi", 
                "export_columns_info" : [
                    {
                        "export_column_name" : "年级",
                        "export_column_count" : "single",
                        "export_row_count" : "single",
                        "db_column_name" : "name",
                    },
                    {
                        "export_column_name" : "id",
                        "export_column_count" : "single",
                        "export_row_count" : "single",
                        "db_column_name" : "id",
                    }
                ]
            }
        },
    ]
}
```
